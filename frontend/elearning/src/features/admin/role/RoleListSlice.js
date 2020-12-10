import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
export const fetchRoles = createAsyncThunk(
  "roles/fetchRoleList",
  async () => {
    // const roles = useSelector(state => state.roles)
    const axios = require("axios").default;
    // axios.get('http://hlong1104.herokuapp.com/api/role', {
    const response = await axios({
      method: "GET",
      url: "http://localhost:8080/api/role",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    }).then((response) => {
      var roles = response.data;
      return roles
    });
    return response;
  }
);

const rolesSlice = createSlice({
  name: "roles",
  // initialState: [{id: 1, name: "A", description: "A"}, {id: 2, name: "B", description: "B"}, {id: 3, name: "C", description: "C"}],
  initialState: [],
  reducers: {
    addARole: (state, action) => {
      console.log(state)

      state.push(action.payload)
    },
    deteleARole:  (state, action) => {
      let index = state.findIndex((role) => (role.id === action.payload))
      state.splice(index, 1)
    },
    editARole:  (state, action) => {
      let role = state.find((role) => (role.id === action.payload.id))
      role.id = action.payload.id
      role.name = action.payload.name
      role.description = action.payload.description

    }
  },
  extraReducers: {
    [fetchRoles.fulfilled]: (state, action) => {
      return action.payload;
    },
  },
});
export default rolesSlice.reducer
export const {addARole, deteleARole, editARole} = rolesSlice.actions
const token =
  "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbkB5YWhvby5jb20iLCJpYXQiOjE2MDcwNTI4MzIsImV4cCI6MTYxNTY5MjgzMn0.11XlYu8VDNQ88fSTy0DwUnGfgrlxKSLdkga16yKj67ZH-_hSNUMAP2Q5xb9R4KCZJpy8Hl-402ZDXVMAOpC3fA";
export const selectAllRole = state => state.roles