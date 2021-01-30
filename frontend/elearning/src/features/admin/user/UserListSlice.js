import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";

export const fetchUsers = createAsyncThunk(
  "users/fetchUserList",
  async () => {
    // const users = useSelector(state => state.users)
    const axios = require("axios").default;
    // axios.get('http://hlong1104.herokuapp.com/api/user', {
    const response = await axios({
      method: "GET",
      url: "http://localhost:8080/api/user",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    }).then((response) => {
      var users = response.data;
      console.log(users)

      return users
    },
    (error) => {console.log(error)}
    );
    return response;
  }
);

export const usersSlice = createSlice({
  name: "users",
  // initialState: [{id: 1, name: "A", description: "A"}, {id: 2, name: "B", description: "B"}, {id: 3, name: "C", description: "C"}],
  initialState: [],
  reducers: {
    addAUser: (state, action) => {
      console.log(state)

      state.push(action.payload)
    },
    deteleAUser:  (state, action) => {
      let index = state.findIndex((user) => (user.id === action.payload))
      state.splice(index, 1)
    },
    editAUser:  (state, action) => {
      let user = state.find((user) => (user.id === action.payload.id))
      user.id = action.payload.id
      user.name = action.payload.name
      user.description = action.payload.description

    }
  },
  extraReducers: {
    [fetchUsers.fulfilled]: (state, action) => {
      return action.payload;
    },
  },
});
export default usersSlice.reducer
export const {addAUser, deteleAUser, editAUser} = usersSlice.actions
const token =
  "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbkB5YWhvby5jb20iLCJpYXQiOjE2MDcwNTI4MzIsImV4cCI6MTYxNTY5MjgzMn0.11XlYu8VDNQ88fSTy0DwUnGfgrlxKSLdkga16yKj67ZH-_hSNUMAP2Q5xb9R4KCZJpy8Hl-402ZDXVMAOpC3fA";
export const selectAllUser = state => state.users