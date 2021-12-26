import { BaseService, Service } from "cl-admin";

@Service("common")
class Common extends BaseService {
	/**
	 * 图片验证码 svg
	 *
	 * @param {*} { height, width }
	 * @returns
	 * @memberof CommonService
	 */
	captcha({ height, width }) {
		return this.request({
			url: "/captcha",
			params: {
				height,
				width
			}
		});
	}

	/**
	 * 文件上传，如果模式是 cloud，返回对应参数
	 *
	 * @returns
	 * @memberof CommonService
	 */
	upload(params) {
		return this.request({
			url: "/upload",
			method: "POST",
			params
		});
	}

}

export default Common;
