package com.etiansoft.ole.sys.service;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.etiansoft.ole.constants.Constants;
import com.etiansoft.ole.dao.SysUserDao;
import com.etiansoft.ole.page.DataTablePage;
import com.etiansoft.ole.po.SysUser;
import com.etiansoft.ole.sys.query.SysUserQuery;
import com.etiansoft.ole.util.ConvertUtil;
import com.etiansoft.ole.util.DateUtil;
import com.etiansoft.ole.util.FilePathProvider;
import com.etiansoft.ole.util.ImageUtil;
import com.etiansoft.ole.util.OleHibernateService;
import com.etiansoft.ole.util.VoTool;
import com.etiansoft.ole.vo.SysUserVo;
import com.etiansoft.tools.hibernate.Pagation;

@Service
@Transactional
public class SysUserService extends OleHibernateService<SysUser> {

	@Autowired
	private SysUserDao sysUserDao;
	@Autowired
	private FilePathProvider filePathProvider;
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Transactional(readOnly = true)
	public DataTablePage getData(SysUserQuery query, DataTablePage page) {
		String currentPage = ConvertUtil.getCurrentPage(page.getiDisplayStart(), page.getiDisplayLength());
		Pagation pagation = sysUserDao.findPage(currentPage, page.getiDisplayLength(), makeHql(query), makeQuery(query));
		DataTablePage dataTablePage = ConvertUtil.generatorTablePage(pagation, page, SysUserVo.class);
		return dataTablePage;
	}

	private String makeHql(SysUserQuery query) {
		String hql = "from SysUser where 1=1 and state=1 and userCode !='admin'";
		String name = query.getName();
		if (StringUtils.isNotEmpty(name)) {
			hql += " and name like :name";
		}
		return hql;
	}

	private LinkedHashMap<String, Object> makeQuery(SysUserQuery query) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		String name = query.getName();
		if (StringUtils.isNotEmpty(name)) {
			params.put("name", "%" + name + "%");
		}
		return params;
	}

	public void update(SysUser user, HttpSession session, String birthday) throws Exception {
		SysUser sysUser = sysUserDao.findById(user.getUserCode());
		sysUser.setName(user.getName());
		sysUser.setSex(user.getSex());
		sysUser.setCellPhone(user.getCellPhone());
		sysUser.setTel(user.getTel());
		sysUser.setMail(user.getMail());
		sysUser.setAddress(user.getAddress());
		sysUser.setZipCode(user.getZipCode());
		sysUser.setBirthday(DateUtil.parse(birthday, "yyyy-MM-dd"));
		sysUser.setHeadIcon(user.getHeadIcon());
		sysUserDao.update(sysUser);
		sysUser.getRoles().toString();
		session.setAttribute(Constants.LOGIN_USER, sysUser);
	}

	public void updatePassword(SysUser user, String password) {
		sysUserDao.updatePassword(user, password);
	}

	public List<SysUser> findbyStatus(Integer status) {
		return sysUserDao.findbyStatus(status);
	}

	public List<SysUserVo> findbyState() {
		List<SysUser> sysUsers = sysUserDao.findbyState();
		return VoTool.convert(sysUsers, SysUserVo.class);
	}

	public String upload(MultipartFile headIcon, int x, int y, int w, int h) throws Exception {
		String relativePath = "headicon" + File.separator + UUID.randomUUID().toString() + '-' + headIcon.getOriginalFilename();
		String imagePath = filePathProvider.getUploadPath(relativePath);
		ImageUtil.scale(headIcon.getInputStream(), imagePath);
		Image img;
		ImageFilter cropFilter;
		BufferedImage bi = ImageIO.read(new File(imagePath));
		int srcWidth = bi.getWidth();// 原图宽度
		int srcHeight = bi.getHeight();// 原图高度
		if (srcWidth >= w && srcHeight >= h) {
			Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
			cropFilter = new CropImageFilter(x, y, w, h);
			img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
			BufferedImage tag = new BufferedImage(w, h, BufferedImage.TYPE_INT_BGR);
			Graphics g = tag.getGraphics();
			g.drawImage(img, 0, 0, null);
			g.dispose();

			ImageIO.write(tag, "JPEG", new File(imagePath));
		}
		return File.separator + relativePath;
	}
	@SuppressWarnings("unchecked")
	public List<SysUser> queryBossName() {
		String sql = " select a.user_code, a.name from sys_user as a , sys_role as b ,sys_user_role as c " +
				     " where a.user_code = c.user_code and b.role_id = c.role_id and b.role_id in (1,6) and a.state = 1 order by a.user_code asc ";
		return this.jdbcTemplate.query(sql,new RowMapper(){

			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				SysUser user = new SysUser();
				user.setUserCode(rs.getString("user_code"));
				user.setName(rs.getString("name"));
				return user;
			}
			
		});
	}
}