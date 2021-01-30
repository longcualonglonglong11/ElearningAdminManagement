import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
export const fetchCourses = createAsyncThunk(
  "courses/fetchCourseList",
  async () => {
    // const courses = useSelector(state => state.courses)
    const axios = require("axios").default;
    // axios.get('http://hlong1104.herokuapp.com/api/course', {
    const response = await axios({
      method: "GET",
      url: "http://localhost:8080/api/course",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    }).then((response) => {
      var courses = response.data;
      return courses
    });
    return response;
  }
);

const coursesSlice = createSlice({
  name: "courses",
  // initialState: [{id: 1, name: "A", description: "A"}, {id: 2, name: "B", description: "B"}, {id: 3, name: "C", description: "C"}],
  initialState: [],
  reducers: {
    addACourse: (state, action) => {
      console.log(state)

      state.push(action.payload)
    },
    deteleACourse:  (state, action) => {
      let index = state.findIndex((course) => (course.id === action.payload))
      state.splice(index, 1)
    },
    editACourse:  (state, action) => {
      let course = state.find((course) => (course.id === action.payload.id))
      course.id = action.payload.id
      course.name = action.payload.name
      course.description = action.payload.description

    }
  },
  extraReducers: {
    [fetchCourses.fulfilled]: (state, action) => {
      return action.payload;
    },
  },
});
export default coursesSlice.reducer
export const {addACourse, deteleACourse, editACourse} = coursesSlice.actions
const token =
  "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbkB5YWhvby5jb20iLCJpYXQiOjE2MDcwNTI4MzIsImV4cCI6MTYxNTY5MjgzMn0.11XlYu8VDNQ88fSTy0DwUnGfgrlxKSLdkga16yKj67ZH-_hSNUMAP2Q5xb9R4KCZJpy8Hl-402ZDXVMAOpC3fA";
export const selectAllCourse = state => state.courses