import Cookies from 'js-cookie'

const TokenKey = 'x-auth-state'

export function isLogined() {
  return Cookies.get(TokenKey) === '1'
}

export function setLogined() {
  return Cookies.set(TokenKey, '1')
}

export function removeLoginedState() {
  return Cookies.remove(TokenKey)
}
