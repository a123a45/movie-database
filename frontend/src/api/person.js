import request from './request'

export function getPersonDetail(id) {
  return request.get(`/person/${id}`)
}

export function getPersonPage(params) {
  return request.get('/person/page', { params })
}
