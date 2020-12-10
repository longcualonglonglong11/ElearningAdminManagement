import { configureStore } from '@reduxjs/toolkit';
import counterReducer from '../features/counter/counterSlice';
import rolesReducer from '../features/admin/role/RoleListSlice'
export default configureStore({
  reducer: {
    counter: counterReducer,
    roles: rolesReducer,
  },
});
