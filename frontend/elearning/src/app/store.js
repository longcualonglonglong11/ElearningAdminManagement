import { configureStore } from '@reduxjs/toolkit';
import counterReducer from '../features/counter/counterSlice';
import rolesReducer from '../features/admin/role/RoleListSlice'
import usersReducer from '../features/admin/user/UserListSlice'
import videosReducer from '../features/admin/video/VideoListSlice'
import coursesReducer from '../features/admin/course/CourseListSlice'

export default configureStore({
  reducer: {
    counter: counterReducer,
    roles: rolesReducer,
    users: usersReducer,
    videos: videosReducer,
    courses: coursesReducer
  },
});
