package com.etiansoft.ole.device.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.etiansoft.ole.constants.Constants;
import com.etiansoft.ole.device.query.DeviceBorrowRecordQuery;
import com.etiansoft.ole.device.service.DeviceBorrowService;
import com.etiansoft.ole.device.service.DeviceService;
import com.etiansoft.ole.page.DataTablePage;
import com.etiansoft.ole.po.Device;
import com.etiansoft.ole.po.DeviceBorrow;
import com.etiansoft.ole.po.SysUser;
import com.etiansoft.ole.sys.service.SysUserService;
import com.etiansoft.ole.vo.DeviceVo;
import com.etiansoft.ole.vo.SysUserVo;

@Controller
public class DeviceController {

	@Autowired
	private DeviceBorrowService deviceBorrowService;

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private DeviceService deviceService;

	@RequestMapping("/device/list")
	public String list() {
		return "device/list";
	}

	@ResponseBody
	@RequestMapping("/device/data")
	public DataTablePage data(Device device, DataTablePage page) {
		return deviceService.getData(device, page);
	}

	@RequestMapping("/device/borrowRecord")
	public String borrowList(ModelMap model, Integer deviceCode) {
		model.put("deviceCode", deviceCode);
		return "device/borrowRecord";
	}

	@ResponseBody
	@RequestMapping("/deviceBorrow/data")
	public DataTablePage data(DeviceBorrowRecordQuery query, DataTablePage page) {
		return deviceBorrowService.getData(query, page);
	}

	@RequestMapping(value = "/device/add")
	public String add() {
		return "device/add";
	}

	@RequestMapping("/device/save")
	public String save(Device device, String buyTime, String expireDate) {
		deviceService.save(device, buyTime, expireDate);
		return "redirect:/device/list";
	}

	@RequestMapping("/device/edit")
	public String edit(Integer deviceCode, ModelMap model) {
		if (deviceCode != null) {
			model.put("edit", true);
			model.put("device", deviceService.findVoById(deviceCode, DeviceVo.class));
		}
		return "device/edit";
	}

	@RequestMapping("/device/update")
	public String update(Device device) {
		device.setRemove(true);
		deviceService.update(device);
		return "redirect:/device/list";
	}

	@RequestMapping("/device/doDelete")
	public String doDelete(Integer deviceCode) {
		Device device = deviceService.findById(deviceCode);
		device.setRemove(false);
		deviceService.update(device);
		return "redirect:/device/list";
	}

	@RequestMapping("/device/returnBack")
	public String returnBack(Integer borrowCode) {
		deviceBorrowService.returnBack(borrowCode);
		return "redirect:/device/borrowRecord";
	}

	@RequestMapping("/device/usedList")
	public String usedList(ModelMap model, HttpSession session) {
		SysUser user = (SysUser) session.getAttribute(Constants.LOGIN_USER);
		model.put("loginName", user.getName());
		return "personalData/usedDevice";
	}

	@RequestMapping(value = "/device/applyBorrow")
	public String applyBorrow(ModelMap model, Integer deviceCode, Integer stockNum) {
		List<SysUserVo> sysUsers = sysUserService.findbyState();
		DeviceVo devices = deviceService.findVoById(deviceCode, DeviceVo.class);
		model.put("sysUsers", sysUsers);
		model.put("devices", devices);
		model.put("stockNum", stockNum);
		return "/device/applyBorrow";
	}

	@RequestMapping(value = "/device/applyBorrowSubmit")
	public String applyBorrowSubmit(DeviceBorrow deviceBorrow, Device device, String userCode, Integer deviceCode, String borrowTime, String returnTime, HttpSession session) {
		deviceBorrowService.applyBorrowSubmit(deviceBorrow, userCode, deviceCode, borrowTime, returnTime, session);
		return "redirect:/device/borrowRecord";
	}

}