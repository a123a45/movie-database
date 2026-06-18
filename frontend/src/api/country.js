import request from './request'

export function getCountryList() {
  return request.get('/country/list')
}
