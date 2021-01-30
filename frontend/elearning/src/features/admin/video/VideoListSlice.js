import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
export const fetchVideos = createAsyncThunk(
  "videos/fetchVideoList",
  async () => {
    // const videos = useSelector(state => state.videos)
    const axios = require("axios").default;
    // axios.get('http://hlong1104.herokuapp.com/api/video', {
    const response = await axios({
      method: "GET",
      url: "http://localhost:8080/api/video",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    }).then((response) => {
      var videos = response.data;
      return videos
    });
    return response;
  }
);

const videosSlice = createSlice({
  name: "videos",
  // initialState: [{id: 1, name: "A", description: "A"}, {id: 2, name: "B", description: "B"}, {id: 3, name: "C", description: "C"}],
  initialState: [],
  reducers: {
    addAVideo: (state, action) => {
      console.log(state)

      state.push(action.payload)
    },
    deteleAVideo:  (state, action) => {
      let index = state.findIndex((video) => (video.id === action.payload))
      state.splice(index, 1)
    },
    editAVideo:  (state, action) => {
      let video = state.find((video) => (video.id === action.payload.id))
      video.id = action.payload.id
      video.name = action.payload.name
      video.description = action.payload.description

    }
  },
  extraReducers: {
    [fetchVideos.fulfilled]: (state, action) => {
      return action.payload;
    },
  },
});
export default videosSlice.reducer
export const {addAVideo, deteleAVideo, editAVideo} = videosSlice.actions
const token =
  "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbkB5YWhvby5jb20iLCJpYXQiOjE2MDcwNTI4MzIsImV4cCI6MTYxNTY5MjgzMn0.11XlYu8VDNQ88fSTy0DwUnGfgrlxKSLdkga16yKj67ZH-_hSNUMAP2Q5xb9R4KCZJpy8Hl-402ZDXVMAOpC3fA";
export const selectAllVideo = state => state.videos