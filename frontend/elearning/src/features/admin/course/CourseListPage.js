import React, {
    useEffect,
    useRef,
    useState,
    useCallback,
    useMemo,
  } from "react";
  import { useSelector, useDispatch } from "react-redux";
  import {
    selectAllCourse,
    fetchCourses,
    addACourse,
    editACourse,
    deteleACourse,
  } from "./CourseListSlice";
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
    const formCourseRef = useRef();
    const courses = useSelector(selectAllCourse);
    const btnAddContainer = "btn-add-container";
    const formAddContainer = "form-add-container";
    useEffect(() => {
      console.log("Main Course fetch");
      ReactDOM.render(
        <AddCourseButton resultMsg="" />,
        document.getElementById(btnAddContainer)
      );
      dispatch(fetchCourses());
    }, []);
    const CourseForm = ({ resultMsg, colorResultMsg }) => {
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
        <div ref={formCourseRef} class="pcoded-content">
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
                        <h2 class="text-uppercase text-center">Add new course</h2>
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
                                    handleAddCourse(senderId, name, description);
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
                                      <AddCourseButton resultMsg="" />,
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
                                  class="ml-5 course-msg"
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
    const handleAddCourse = (id, name, description) => {
      clientRef.sendMessage(
        "/app/add-course",
  
        JSON.stringify({
          senderId: id,
          course: { name, description },
        })
      );
    };
    const handleEditCourse = ({ courseId, name, description }) => {
      console.log("AAAAAAAAaa: ", senderId);
      clientRef.sendMessage(
        "/app/edit-course",
  
        JSON.stringify({
          senderId: senderId,
          course: { id: courseId, name, description },
        })
      );
    };
    const handleDelCourse = (id) => {
      console.log(clientRef);
      clientRef.sendMessage(`/app/delete-course/${id}`);
    };
  
    const CourseTable = ({ courses }) => {
      const remakeEditRow = ({
        name,
        description,
        rowCourseRef,
        index,
        courseId,
      }) => {
        const curRowContainer = rowCourseRef[index].current.querySelectorAll("td");
        const nameRow = curRowContainer[0];
        const descriptionRow = curRowContainer[1];
  
        curRowContainer[2].innerHTML = `<a class="btn btn-sm btn-info btn-round py-1 font-weight-bold text-white">Edit</a><a class="btn btn-sm btn-danger btn-round py-1 font-weight-bold text-white">Delete</a>`;
        const curBtnContainer = curRowContainer[2].querySelectorAll("a");
        curBtnContainer[0].addEventListener("click", () =>
          createEditCourseSegment({ index, courseId })
        );
        curBtnContainer[1].addEventListener("click", () =>
          handleDelCourse(courseId)
        );
        nameRow.innerHTML = name;
        descriptionRow.innerHTML = description;
        console.log(nameRow);
  
        console.log(curRowContainer);
      };
      const createEditCourseSegment = ({ index, courseId }) => {
        // rowCourseRef[index].current.innerHTML = '<input value="ye"/>'
        const curRowContainer = rowCourseRef[index].current.querySelectorAll("td");
        const nameRow = curRowContainer[0];
        const descriptionRow = curRowContainer[1];
  
        const backupName = nameRow.innerText;
        const backupDescription = descriptionRow.innerText;
        let backupData = {
          name: backupName,
          description: backupDescription,
          rowCourseRef,
          index,
          courseId,
        };
  
        curRowContainer[2].innerHTML = `<a class="btn btn-sm btn-info btn-round py-1 font-weight-bold text-white">${"Save"}</a><a class="btn btn-sm btn-danger btn-round py-1 font-weight-bold text-white">Cancle</a>`;
        const curBtnContainer = curRowContainer[2].querySelectorAll("a");
        curBtnContainer[0].addEventListener(
          "click",
          () =>
            handleEditCourse({
              courseId,
              name: nameRow.querySelector("input").value,
              description: descriptionRow.querySelector("input").value,
            })
          // ,
          // remakeEditRow({
          //   name: nameRow.querySelector("input").value,
          //   description: descriptionRow.querySelector("input").value,
          //   rowCourseRef,
          //   index,
          //   courseId
          // })
        );
        // curBtnContainer[0].addEventListener("click", () =>
        //   remakeEditRow({
  
        //     name: nameRow.querySelector("input").value,
        //     description: descriptionRow.querySelector("input").value,
        //     rowCourseRef,
        //     index,
        //     courseId
        //   })
        // );
        curBtnContainer[1].addEventListener("click", () =>
          remakeEditRow(backupData)
        );
        nameRow.innerHTML = `<input value='${backupName}'/>`;
        descriptionRow.innerHTML = `<input value='${backupDescription}'/>`;
  
        // rowCourseRef[index].current.style.color = 'red'
      };
      var rowCourseRef = [];
      if (courses)
        for (let i = 0; i < courses.length; i++) {
          rowCourseRef[i] = React.createRef();
        }
      console.log(rowCourseRef);
  
      return (
        <div className="card-block table-border-style">
          {console.log("course table render")}
          <div className="table-responsive">
            <table className="table table-bordered">
              <thead>
                <tr>
                  <th>No</th>
                  <th>Title</th>
                  <th>Image</th>
                  <th>Category</th>
                  <th>Description</th>

                  <th>Price</th>
                  <th>Discount</th>
                  <th>Promotion Price</th>
                  <th>Last updated</th>

                  <th>Sum of lecuturer</th>
                  <th>Length of videos</th>
                 
                  <th>Content</th>

                  <th>Option</th>
                </tr>
              </thead>
              <tbody id="tbodyCourse">
                {courses.map((course, index) => (
                  <tr ref={rowCourseRef[index]} key={course.id} class="text-left">
                    <th>{index + 1}</th>
                    <td> {course.title}</td>
                    <td><img style={{
                        borderRadius: 10
                    }} src={course.image} width="120" height="75"></img> </td>
                    <td>{course.categoryName}</td>
  
                    <td>{course.description}</td>
                    <td>{course.price}</td>
                    <td>{course.discount}</td>
                    <td>{course.promotionPrice}</td>
                    <td>{course.lastUpdate}</td>
                    <td>{course.lectureCount}</td>
                    <td>{course.lengthVideos}</td>
                    <td>{course.content}</td>

                    <td>
                      <a
                        className="btn btn-sm btn-info btn-round py-1 font-weight-bold text-white"
                        onClick={() => {
                          createEditCourseSegment({ index, courseId: course.id });
                        }}
                      >
                        Edit
                      </a>
                      <a
                        onClick={() => {
                          handleDelCourse(course.id);
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
    const AddCourseButton = ({ resultMsg, colorResultMsg }) => {
      useEffect(() => {}, []);
      return (
        <>
          {console.log("btn add new course render")}
          <a
            ref={btnAddRef}
            onClick={() => {
              console.log(formCourseRef);
              // formCourseRef.current.remove();
              ReactDOM.unmountComponentAtNode(
                document.getElementById(btnAddContainer)
              );
  
              ReactDOM.render(
                <CourseForm
                  resultMsg={resultMsg}
                  colorResultMsg={colorResultMsg}
                />,
                document.querySelector(`#${formAddContainer}`)
              );
            }}
            className="btn btn-sm btn-primary float-left text-white"
          >
            ADD NEW COURSE
          </a>
        </>
      );
    };
    const handleResultMessage = (message, color) => {
      ReactDOM.render(
        <CourseForm resultMsg={message} colorResultMsg={color} />,
        document.getElementById(formAddContainer)
      );
    };
    return (
      <div className="Course-list">
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
                          <a>Course List</a>
                        </li>
                      </ul>
                    </div>
                  </div>
  
                  <div className="col-sm-12">
                    <div className="card px-3">
                      <div className="card-header px-0 pb-2">
                        <h2 className="text-uppercase text-center">Course List</h2>
                        <div id={btnAddContainer}></div>
                      </div>
  
                      <CourseTable courses={courses} />
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
            "/topic/course-topic/add",
            "/topic/course-topic/delete",
            "/topic/course-topic/edit",
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
            const actionAdd = "course/add";
            const actionDelete = "course/del";
            const actionEdit = "course/edit";
  
            switch (msg.type) {
              case actionAdd:
                if (msg.status === succeedState) {
                  dispatch(addACourse(msg.course));
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
                  dispatch(deteleACourse(msg.course.id));
                } else if (msg.status === errorState) {
                  console.log(msg.error);
                }
                break;
              case actionEdit:
                console.log(msg);
                if (msg.status === succeedState) {
                  dispatch(editACourse(msg.course));
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
  