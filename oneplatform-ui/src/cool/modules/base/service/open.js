import { BaseService, Service } from "cl-admin";

@Service("base/open")
class Open extends BaseService {
	/**
	 * 用户登录
	 *
	 * @param {*} { username, password, captchaId, verifyCode }
	 * @returns
	 * @memberof CommonService
	 */
	userLogin({ username, password, captchaId, verifyCode }) {
		return this.request({
			url: "/login",
			method: "POST",
			data: {
				username,
				password,
				captchaId,
				verifyCode
			}
		});
	}

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
	 * 刷新 token
	 * @param {string} token
	 */
	refreshToken(token) {
		return this.request({
			url: "/refreshToken",
			params: {
				refreshToken: token
			}
		});
	}
}

export default Open;
