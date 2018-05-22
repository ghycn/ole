/**
 * @项目名称: estate-web
 * @文件名称: VoTool.java
 * @author tianlihu
 * @Date: 2015-5-21
 * @Copyright: 2015 www.etiansoft.com Inc. All rights reserved.
 * 注意：本内容仅限于北京逸天科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.etiansoft.ole.util;

import java.util.ArrayList;
import java.util.List;

import com.etiansoft.tools.vo.Vo;

public class VoTool {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <VO extends Vo<PO>, PO> List<VO> convert(List<PO> pos, Class<VO> voClass) {
		try {
			List vos = new ArrayList(pos.size());
			for (Object po : pos) {
				if (po == null) {
					continue;
				}
				Vo result = voClass.newInstance();
				result.from(po);
				vos.add(result);
			}
			return vos;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
