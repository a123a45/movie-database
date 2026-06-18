import request from './request'

export function getCarouselList() {
  return request.get('/carousel/list')
}
