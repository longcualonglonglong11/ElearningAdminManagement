import React, {
  useEffect,
  useRef,
  useState,
  useCallback,
  useMemo,
} from "react";
import { useSelector, useDispatch } from "react-redux";
import {
  selectAllVideo,
  fetchVideos,
  addAVideo,
  editAVideo,
  deteleAVideo,
} from "./VideoListSlice";
import SockJsClient from "react-stomp";

import ReactDOM from "react-dom";
import { Link } from "react-router-dom";
var senderId = Math.floor(Math.random() * 3);
console.log("Sender ID:", senderId);

//Global variable
var clientRef;
export default function () {
  const dispatch = useDispatch();
  const btnAddRef = useRef();
  const formVideoRef = useRef();
  const videos = useSelector(selectAllVideo);
  const btnAddContainer = "btn-add-container";
  const formAddContainer = "form-add-container";
  useEffect(() => {
    console.log("Main Video fetch");
    ReactDOM.render(
      <AddVideoButton resultMsg="" />,
      document.getElementById(btnAddContainer)
    );
    dispatch(fetchVideos());
  }, []);
  const VideoForm = ({ resultMsg, colorResultMsg }) => {
    const nameInputRef = useRef();
    const mesessageRef = useRef();
    const [name, setName] = useState("");
    const onNameChanged = (e) => setName(e.target.value);
    const [description, setDescription] = useState("");
    const onDescriptionChanged = (e) => setDescription(e.target.value);
    const resetForm = () => {
      setName("");
      setDescription("");
    };

    useEffect(() => {
      nameInputRef.current.focus();
    }, []);
    return (
      <div ref={formVideoRef} class="pcoded-content">
        {console.log("form render")}
        <BigThing />

        <div class="pcoded-inner-content">
          {/* <!-- Main-body start --> */}
          <div class="main-body">
            <div class="page-body">
              <div class="row">
                <div class="col-sm-12">
                  <div class="card">
                    <div class="card-header">
                      <h2 class="text-uppercase text-center">Add new video</h2>
                    </div>
                    <div class="card-block">
                      <form action="" method="post">
                        <div class="row">
                          <div class="col-md-6 m-auto">
                            <div class="form-group">
                              <label class="float-left">Name</label>
                              <input
                                onChange={onNameChanged}
                                ref={nameInputRef}
                                type="text"
                                path="name"
                                id="name"
                                class="form-control"
                                value={name}
                              />
                            </div>
                            <div class="form-group">
                              <label class="float-left">Description</label>
                              <input
                                value={description}
                                onChange={onDescriptionChanged}
                                type="text"
                                path="description"
                                id="desc"
                                class="form-control"
                              />
                            </div>
                            <div class="form-group mt-3 float-left">
                              <button
                                type="button"
                                onClick={() => {
                                  handleAddVideo(senderId, name, description);
                                  resetForm();
                                }}
                                class="btn btn-primary m-b-0"
                                disabled={
                                  name.length === 0 && description.length === 0
                                }
                              >
                                Save
                              </button>
                              <a
                                onClick={() => {
                                  // ReactDOM.render(<></>, document.querySelector("#hohoho"));
                                  ReactDOM.render(
                                    <AddVideoButton resultMsg="" />,
                                    document.querySelector(
                                      `#${btnAddContainer}`
                                    )
                                  );
                                  ReactDOM.unmountComponentAtNode(
                                    document.querySelector(
                                      `#${formAddContainer}`
                                    )
                                  );
                                }}
                                class="ml-2 btn btn-secondary text-white"
                              >
                                Cancel
                              </a>
                              <a
                                class="ml-5 video-msg"
                                ref={mesessageRef}
                                style={{
                                  color: colorResultMsg,
                                }}
                              >
                                {resultMsg}
                              </a>
                            </div>
                          </div>
                        </div>
                      </form>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  };
  const [state, setstate] = useState("initialState");

  const BigThing = useCallback(
    () => (a) => (
      <div>
        {setTimeout(() => {
          console.log("Big thing render");
        }, a)}
        <h1>{state}</h1>
      </div>
    ),
    []
  );

  const token =
    "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbkB5YWhvby5jb20iLCJpYXQiOjE2MDc0MTUyMzYsImV4cCI6MTYxNjA1NTIzNn0.eMohZIX9PyAaCq_TtSM3P8jCS30mg_W4yV_e6vXYd6zZyyCruRfHNfwjJ3zTuXgdSk76qd9Cfw62b9rIFihH2A";
  const handleAddVideo = (id, name, description) => {
    clientRef.sendMessage(
      "/app/add-video",

      JSON.stringify({
        senderId: id,
        video: { name, description },
      })
    );
  };
  const handleEditVideo = ({ videoId, name, description }) => {
    console.log("AAAAAAAAaa: ", senderId);
    clientRef.sendMessage(
      "/app/edit-video",

      JSON.stringify({
        senderId: senderId,
        video: { id: videoId, name, description },
      })
    );
  };
  const handleDelVideo = (id) => {
    console.log(clientRef);
    clientRef.sendMessage(`/app/delete-video/${id}`);
  };

  const VideoTable = ({ videos }) => {
    const remakeEditRow = ({
      name,
      description,
      rowVideoRef,
      index,
      videoId,
    }) => {
      const curRowContainer = rowVideoRef[index].current.querySelectorAll("td");
      const nameRow = curRowContainer[0];
      const descriptionRow = curRowContainer[1];

      curRowContainer[2].innerHTML = `<a class="btn btn-sm btn-info btn-round py-1 font-weight-bold text-white">Edit</a><a class="btn btn-sm btn-danger btn-round py-1 font-weight-bold text-white">Delete</a>`;
      const curBtnContainer = curRowContainer[2].querySelectorAll("a");
      curBtnContainer[0].addEventListener("click", () =>
        createEditVideoSegment({ index, videoId })
      );
      curBtnContainer[1].addEventListener("click", () =>
        handleDelVideo(videoId)
      );
      nameRow.innerHTML = name;
      descriptionRow.innerHTML = description;
      console.log(nameRow);

      console.log(curRowContainer);
    };
    const createEditVideoSegment = ({ index, videoId }) => {
      // rowVideoRef[index].current.innerHTML = '<input value="ye"/>'
      const curRowContainer = rowVideoRef[index].current.querySelectorAll("td");
      const nameRow = curRowContainer[0];
      const descriptionRow = curRowContainer[1];

      const backupName = nameRow.innerText;
      const backupDescription = descriptionRow.innerText;
      let backupData = {
        name: backupName,
        description: backupDescription,
        rowVideoRef,
        index,
        videoId,
      };

      curRowContainer[2].innerHTML = `<a class="btn btn-sm btn-info btn-round py-1 font-weight-bold text-white">${"Save"}</a><a class="btn btn-sm btn-danger btn-round py-1 font-weight-bold text-white">Cancle</a>`;
      const curBtnContainer = curRowContainer[2].querySelectorAll("a");
      curBtnContainer[0].addEventListener(
        "click",
        () =>
          handleEditVideo({
            videoId,
            name: nameRow.querySelector("input").value,
            description: descriptionRow.querySelector("input").value,
          })
        // ,
        // remakeEditRow({
        //   name: nameRow.querySelector("input").value,
        //   description: descriptionRow.querySelector("input").value,
        //   rowVideoRef,
        //   index,
        //   videoId
        // })
      );
      // curBtnContainer[0].addEventListener("click", () =>
      //   remakeEditRow({

      //     name: nameRow.querySelector("input").value,
      //     description: descriptionRow.querySelector("input").value,
      //     rowVideoRef,
      //     index,
      //     videoId
      //   })
      // );
      curBtnContainer[1].addEventListener("click", () =>
        remakeEditRow(backupData)
      );
      nameRow.innerHTML = `<input value='${backupName}'/>`;
      descriptionRow.innerHTML = `<input value='${backupDescription}'/>`;

      // rowVideoRef[index].current.style.color = 'red'
    };
    var rowVideoRef = [];
    if (videos)
      for (let i = 0; i < videos.length; i++) {
        rowVideoRef[i] = React.createRef();
      }
    console.log(rowVideoRef);

    return (
      <div className="card-block table-border-style">
        {console.log("video table render")}
        <div className="table-responsive">
          <table className="table table-bordered">
            <thead>
              <tr>
                <th>No</th>
                <th>Title</th>
                <th>URL</th>
                <th>Length</th>
                <th>Course Name</th>

                <th>Option</th>
              </tr>
            </thead>
            <tbody id="tbodyVideo">
              {videos.map((video, index) => (
                <tr ref={rowVideoRef[index]} key={video.id} class="text-left">
                  <th>{index + 1}</th>
                  <td> {video.title}</td>
                  <td>{video.url}</td>
                  <td>{video.length}</td>

                  <td>{video.courseName}</td>
                  <td>
                    <a
                      className="btn btn-sm btn-info btn-round py-1 font-weight-bold text-white"
                      onClick={() => {
                        createEditVideoSegment({ index, videoId: video.id });
                      }}
                    >
                      Edit
                    </a>
                    <a
                      onClick={() => {
                        handleDelVideo(video.id);
                      }}
                      className="btn btn-sm btn-danger btn-round py-1 font-weight-bold text-white"
                    >
                      Delete
                    </a>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    );
  };
  const AddVideoButton = ({ resultMsg, colorResultMsg }) => {
    useEffect(() => {}, []);
    return (
      <>
        {console.log("btn add new video render")}
        <a
          ref={btnAddRef}
          onClick={() => {
            console.log(formVideoRef);
            // formVideoRef.current.remove();
            ReactDOM.unmountComponentAtNode(
              document.getElementById(btnAddContainer)
            );

            ReactDOM.render(
              <VideoForm
                resultMsg={resultMsg}
                colorResultMsg={colorResultMsg}
              />,
              document.querySelector(`#${formAddContainer}`)
            );
          }}
          className="btn btn-sm btn-primary float-left text-white"
        >
          ADD NEW VIDEO
        </a>
      </>
    );
  };
  const handleResultMessage = (message, color) => {
    ReactDOM.render(
      <VideoForm resultMsg={message} colorResultMsg={color} />,
      document.getElementById(formAddContainer)
    );
  };
  return (
    <div className="Video-list">
      <div id={formAddContainer}></div>
      <div className="pcoded-content ">
        <div className="pcoded-inner-content">
          {/* <!-- Main-body start --> */}
          <div className="main-body">
            <div className="page-body">
              <div className="row">
                <div className="col-md-12 mb-2">
                  <div className="page-header-breadcrumb">
                    <ul className="breadcrumb-title">
                      <li className="breadcrumb-item">
                        <Link to="/admin">
                          <i className="feather icon-home"></i> Homepage
                        </Link>
                      </li>
                      <li className="breadcrumb-item">
                        <a>Video List</a>
                      </li>
                    </ul>
                  </div>
                </div>

                <div className="col-sm-12">
                  <div className="card px-3">
                    <div className="card-header px-0 pb-2">
                      <h2 className="text-uppercase text-center">Video List</h2>
                      <div id={btnAddContainer}></div>
                    </div>

                    <VideoTable videos={videos} />
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <SockJsClient
        url="http://localhost:8080/socket"
        headers={{
          Authorization: `Bearer ${token}`,
        }}
        cc="cc"
        topics={[
          "/topic/video-topic/add",
          "/topic/video-topic/delete",
          "/topic/video-topic/edit",
        ]}
        onConnect={() => {
          console.log("Connected!!!");
        }}
        onDisconnect={() => {
          console.log("Disconnected :((((");
        }}
        onMessage={(msg) => {
          console.log(msg);
          const failureMsg = "Action Failed";
          const successeMsg = "Action Successed";
          const successColor = "green";
          const failureColor = "red";
          const succeedState = "success";
          const errorState = "error";
          const actionAdd = "video/add";
          const actionDelete = "video/del";
          const actionEdit = "video/edit";

          switch (msg.type) {
            case actionAdd:
              if (msg.status === succeedState) {
                dispatch(addAVideo(msg.video));
                if (msg.senderId === senderId)
                  handleResultMessage(successeMsg, successColor);
              } else if (
                msg.status === errorState &&
                msg.senderId === senderId
              ) {
                handleResultMessage(failureMsg, failureColor);
              }
              break;
            case actionDelete:
              if (msg.status === succeedState) {
                dispatch(deteleAVideo(msg.video.id));
              } else if (msg.status === errorState) {
                console.log(msg.error);
              }
              break;
            case actionEdit:
              console.log(msg);
              if (msg.status === succeedState) {
                dispatch(editAVideo(msg.video));
              } else if (msg.status === errorState) {
                console.log(msg.error);
              }
              break;
            default:
              break;
          }
        }}
        ref={(client) => {
          clientRef = client;
        }}
      />
    </div>
  );
}
