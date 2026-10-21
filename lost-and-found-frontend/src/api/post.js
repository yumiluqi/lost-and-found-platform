import request from './request'

export const getPostList = (params) => {
  return request.get('/post/list', { params })
}

export const getPostDetail = (postId) => {
  return request.get(`/post/${postId}`)
}

export const addPost = (data) => {
  return request.post('/post/add', data)
}

export const claimPost = (postId) => {
  return request.put(`/post/claim/${postId}`)
}

export const deletePost = (postId) => {
  return request.delete(`/post/${postId}`)
}

export const getMyPosts = (userId) => {
  return request.get(`/post/my/${userId}`)
}