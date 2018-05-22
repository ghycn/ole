/**
 * @项目名称: ole
 * @文件名称: OleHibernateService.java
 * @author tianlihu
 * @Date: 2015-5-27
 * @Copyright: 2015 www.etiansoft.com Inc. All rights reserved.
 * 注意：本内容仅限于北京逸天科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.etiansoft.ole.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.etiansoft.tools.hibernate.HibernateService;
import com.etiansoft.tools.vo.Vo;

public class OleHibernateService<PO> extends HibernateService<PO> {

	public <VO extends Vo<PO>> VO findVoById(Serializable id, Class<VO> voClass) {
		PO po = dao.findById(id);
		if (po == null) {
			return null;
		}
		try {
			VO vo = voClass.newInstance();
			vo.from(po);
			return vo;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public <VO extends Vo<PO>> List<VO> findAllVos(Class<VO> voClass) {
		List<PO> pos = dao.findAll();
		try {
			List<VO> results = new ArrayList<VO>(pos.size());
			for (Object po : pos) {
				VO result = voClass.newInstance();
				result.from((PO) po);
				results.add(result);
			}
			return (List<VO>) results;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
