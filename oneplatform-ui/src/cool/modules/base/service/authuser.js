import { BaseService, Service } from "cl-admin";

@Service("user")
class AuthUser extends BaseService {
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
	 * 用户退出
	 */
	userLogout() {
		return this.request({
			url: "/logout",
			method: "POST"
		});
	}

	/**
	 * 用户信息
	 *
	 * @returns
	 * @memberof CommonService
	 */
	userInfo() {
		return this.request({
			url: "/person"
		});
	}

	/**
	 * 用户信息修改
	 *
	 * @param {*} params
	 * @returns
	 * @memberof CommonService
	 */
	userUpdate(params) {
		return this.request({
			url: "/personUpdate",
			method: "POST",
			data: {
				...params
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

export default AuthUser;
