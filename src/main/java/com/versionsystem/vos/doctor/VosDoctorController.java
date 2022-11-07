package com.versionsystem.vos.doctor;

import com.versionsystem.common.DataList;
import com.versionsystem.common.DataMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("vosDoctor")
public class VosDoctorController {

	@Autowired
	private VosDoctorService vosDoctorService;

	@RequestMapping("read")
	public DataList read(@RequestBody DataMap map) {
		if (map.isEmpty()) {
			return null;
		}
		return vosDoctorService.read(map);
	}

	@RequestMapping("pdf")
	public void pdf(@RequestBody DataMap map) throws Exception {
		vosDoctorService.pdf(vosDoctorService.read(map));
	}

	@RequestMapping("area")
	public DataList area() {
		return vosDoctorService.area();
	}

	@RequestMapping("district")
	public DataList district(@RequestBody DataMap map) {
		if (map.isEmpty()) {
			return null;
		}
		return vosDoctorService.district(map);
	}

	@RequestMapping("specialty")
	public DataList specialty() {
		return vosDoctorService.specialty();
	}

}
