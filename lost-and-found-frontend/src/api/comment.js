import request from './request'

export const getCommentList = (params) => {
  return request.get('/comment/list', { params })
}

export const addComment = (data) => {
  return request.post('/comment/add', data)
}