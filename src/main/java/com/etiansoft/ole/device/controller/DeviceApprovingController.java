package com.etiansoft.ole.device.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.etiansoft.ole.device.query.DeviceBorrowRecordQuery;
import com.etiansoft.ole.device.service.DeviceBorrowService;
import com.etiansoft.ole.device.service.DeviceService;
import com.etiansoft.ole.page.DataTablePage;
import com.etiansoft.ole.po.DeviceBorrow;
import com.etiansoft.ole.sys.service.SysUserService;
import com.etiansoft.ole.vo.DeviceBorrowVo;

@Controller
public class DeviceApprovingController {

	@Autowired
	private DeviceBorrowService deviceBorrowService;

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private DeviceService deviceService;

	@RequestMapping("/approving/device/list")
	public String list() {
		return "device/approving/borrowRecord";
	}

	@ResponseBody
	@RequestMapping("/approving/deviceBorrow/data")
	public DataTablePage data(DeviceBorrowRecordQuery query, DataTablePage page) {
		return deviceBorrowService.getData(query, page);
	}

	@RequestMapping(value = "/approving/deviceBorrow/add")
	public String add(ModelMap model) {
		model.put("deviceList", deviceService.getDeviceList());
		model.put("userList", sysUserService.findAll());
		return "device/approving/add";
	}

	@RequestMapping("/approving/deviceBorrow/saveOrUpdate")
	public String save(DeviceBorrow deviceBorrow, Integer deviceCode, String userCode) {
		if(deviceBorrow.getBorrowCode()!=null){
			deviceBorrowService.update(deviceBorrow);
		}else{
			deviceBorrowService.deviceBorrowSave(deviceBorrow,userCode,deviceCode);
		}
		return "redirect:/approving/device/list";
	}

	@RequestMapping("/approving/deviceBorrow/edit")
	public String edit(Integer borrowCode, ModelMap model) {
		if (borrowCode != null) {
			model.put("edit", true);
			model.put("device", deviceBorrowService.findVoById(borrowCode,  DeviceBorrowVo.class));
		}
		return "device/approving/edit";
	}

	@RequestMapping("/approving/deviceBorrow/info")
	public String info(Integer borrowCode, ModelMap model) {
		if (borrowCode != null) {
			model.put("edit", true);
			DeviceBorrow device=deviceBorrowService.findById(borrowCode);
			model.put("device", device);
		}
		return "device/approving/info";
	}
	@RequestMapping("/approving/deviceBorrow/update")
	public String update(DeviceBorrow deviceBorrow) {
//		device.setRemove(true);
		deviceBorrowService.update(deviceBorrow);
		return "redirect:/approving/device/list";
	}

	@RequestMapping("/approving/deviceBorrow/delete")
	public String doDelete(Integer borrowCode) {
//		DeviceBorrow deviceBorrow = deviceBorrowService.findById(deviceCode);
//		deviceBorrow.setRemove(false);
		deviceBorrowService.delete(borrowCode);
		return "redirect:/approving/device/list";
	}

	@RequestMapping("/approving/deviceBorrow/retuBack")
	public String returnBack(Integer borrowCode) {
		deviceBorrowService.returnBack(borrowCode);
		return "redirect:/approving/device/list";
	}
}