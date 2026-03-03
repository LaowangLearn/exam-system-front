import request from '../utils/request'

// 用户类型定义
export interface User {
  id: number
  username: string
  name: string
  email: string
  phone: string
  role: string
  status: string
}

// 用户管理API服务
const userService = {
  // 获取用户列表
  getUsers: () => {
    return request.get<User[]>('http://120.46.200.253:8080/api/oa/users')
  },

  // 搜索用户
  searchUsers: (keyword: string) => {
    return request.get<User[]>('http://120.46.200.253:8080/api/oa/users', { params: { keyword } })
  },

  // 获取单个用户
  getUser: (id: number) => {
    return request.get<User>(`http://120.46.200.253:8080/api/oa/users/${id}`)
  },

  // 添加用户
  addUser: (user: Omit<User, 'id'>) => {
    return request.post<User>('http://120.46.200.253:8080/api/oa/users', user)
  },

  // 更新用户
  updateUser: (id: number, user: Partial<User>) => {
    return request.put<User>(`http://120.46.200.253:8080/api/oa/users/${id}`, user)
  },

  // 删除用户
  deleteUser: (id: number) => {
    return request.delete(`http://120.46.200.253:8080/api/oa/users/${id}`)
  }
}

export default userService
