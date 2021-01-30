import React, {
  useEffect,
  useRef,
  useState,
  useCallback,
  useMemo,
} from "react";
import { useSelector, useDispatch } from "react-redux";
import {
  selectAllUser,
  fetchUsers,
  addAUser,
  editAUser,
  deteleAUser,
} from "./UserListSlice";
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
  const formUserRef = useRef();
  const users = useSelector(selectAllUser);
  console.log(users)
  const btnAddContainer = "btn-add-container";
  const formAddContainer = "form-add-container";
  useEffect(() => {
    console.log("Main User fetch");
    ReactDOM.render(
      <AddUserButton resultMsg="" />,
      document.getElementById(btnAddContainer)
    );
    dispatch(fetchUsers());
  }, []);
  const UserForm = ({ resultMsg, colorResultMsg }) => {
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
      <div ref={formUserRef} class="pcoded-content">
        {console.log("form render")}

        <div class="pcoded-inner-content">
          {/* <!-- Main-body start --> */}
          <div class="main-body">
            <div class="page-body">
              <div class="row">
                <div class="col-sm-12">
                  <div class="card">
                    <div class="card-header">
                      <h2 class="text-uppercase text-center">Add new user</h2>
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
                                  handleAddUser(senderId, name, description);
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
                                    <AddUserButton resultMsg="" />,
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
                                class="ml-5 user-msg"
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
  const handleAddUser = (id, name, description) => {
    clientRef.sendMessage(
      "/app/add-user",

      JSON.stringify({
        senderId: id,
        user: { name, description },
      })
    );
  };
  const handleEditUser = ({ userId, name, description }) => {
    console.log("AAAAAAAAaa: ", senderId);
    clientRef.sendMessage(
      "/app/edit-user",

      JSON.stringify({
        senderId: senderId,
        user: { id: userId, name, description },
      })
    );
  };
  const handleDelUser = (id) => {
    console.log(clientRef);
    clientRef.sendMessage(`/app/delete-user/${id}`);
  };

  const UserTable = ({ users }) => {
    const remakeEditRow = ({
      name,
      description,
      rowUserRef,
      index,
      userId,
    }) => {
      const curRowContainer = rowUserRef[index].current.querySelectorAll("td");
      const nameRow = curRowContainer[0];
      const descriptionRow = curRowContainer[1];

      curRowContainer[2].innerHTML = `<a class="btn btn-sm btn-info btn-round py-1 font-weight-bold text-white">Edit</a><a class="btn btn-sm btn-danger btn-round py-1 font-weight-bold text-white">Delete</a>`;
      const curBtnContainer = curRowContainer[2].querySelectorAll("a");
      curBtnContainer[0].addEventListener("click", () =>
        createEditUserSegment({ index, userId })
      );
      curBtnContainer[1].addEventListener("click", () => handleDelUser(userId));
      nameRow.innerHTML = name;
      descriptionRow.innerHTML = description;
      console.log(nameRow);

      console.log(curRowContainer);
    };
    const createEditUserSegment = ({ index, userId }) => {
      // rowUserRef[index].current.innerHTML = '<input value="ye"/>'
      const curRowContainer = rowUserRef[index].current.querySelectorAll("td");
      const nameRow = curRowContainer[0];
      const descriptionRow = curRowContainer[1];

      const backupName = nameRow.innerText;
      const backupDescription = descriptionRow.innerText;
      let backupData = {
        name: backupName,
        description: backupDescription,
        rowUserRef,
        index,
        userId,
      };

      curRowContainer[2].innerHTML = `<a class="btn btn-sm btn-info btn-round py-1 font-weight-bold text-white">${"Save"}</a><a class="btn btn-sm btn-danger btn-round py-1 font-weight-bold text-white">Cancle</a>`;
      const curBtnContainer = curRowContainer[2].querySelectorAll("a");
      curBtnContainer[0].addEventListener(
        "click",
        () =>
          handleEditUser({
            userId,
            name: nameRow.querySelector("input").value,
            description: descriptionRow.querySelector("input").value,
          })
        // ,
        // remakeEditRow({
        //   name: nameRow.querySelector("input").value,
        //   description: descriptionRow.querySelector("input").value,
        //   rowUserRef,
        //   index,
        //   userId
        // })
      );
      // curBtnContainer[0].addEventListener("click", () =>
      //   remakeEditRow({

      //     name: nameRow.querySelector("input").value,
      //     description: descriptionRow.querySelector("input").value,
      //     rowUserRef,
      //     index,
      //     userId
      //   })
      // );
      curBtnContainer[1].addEventListener("click", () =>
        remakeEditRow(backupData)
      );
      nameRow.innerHTML = `<input value='${backupName}'/>`;
      descriptionRow.innerHTML = `<input value='${backupDescription}'/>`;

      // rowUserRef[index].current.style.color = 'red'
    };
    var rowUserRef = [];
    if (users)
      for (let i = 0; i < users.length; i++) {
        rowUserRef[i] = React.createRef();
      }
    console.log(rowUserRef);

    return (
      <div className="card-block table-border-style">
        {console.log("user table render")}
        <div className="table-responsive">
          <table className="table table-bordered">
            <thead>
              <tr>
                <th>No</th>
                <th>Fullname</th>
                <th>Avatar</th>
                <th>Phone</th>
                <th>Address</th>
                <th>Role name</th>
                <th>Balance</th>

                <th>Option</th>
              </tr>
            </thead>
            <tbody id="tbodyUser">
              {users.map((user, index) => (
                <tr ref={rowUserRef[index]} key={user.id} class="text-left">
                  <th>{index + 1}</th>
                  <td> {user.fullname}</td>
                  <td><img width="100" height="75" src={user.avatar}></img></td>
                  <td> {user.phone}</td>
                  <td> {user.address}</td>
                  <td> {user.roleName}</td>
                  <td> {user.balance}</td>


                  <td>
                    <a
                      className="btn btn-sm btn-info btn-round py-1 font-weight-bold text-white"
                      onClick={() => {
                        createEditUserSegment({ index, userId: user.id });
                      }}
                    >
                      Edit
                    </a>
                    <a
                      onClick={() => {
                        handleDelUser(user.id);
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
  const AddUserButton = ({ resultMsg, colorResultMsg }) => {
    useEffect(() => {}, []);
    return (
      <>
        {console.log("btn add new user render")}
        <a
          ref={btnAddRef}
          onClick={() => {
            console.log(formUserRef);
            // formUserRef.current.remove();
            ReactDOM.unmountComponentAtNode(
              document.getElementById(btnAddContainer)
            );

            ReactDOM.render(
              <UserForm
                resultMsg={resultMsg}
                colorResultMsg={colorResultMsg}
              />,
              document.querySelector(`#${formAddContainer}`)
            );
          }}
          className="btn btn-sm btn-primary float-left text-white"
        >
          ADD NEW USER
        </a>
      </>
    );
  };
  const handleResultMessage = (message, color) => {
    ReactDOM.render(
      <UserForm resultMsg={message} colorResultMsg={color} />,
      document.getElementById(formAddContainer)
    );
  };
  return (
    <div className="User-list">
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
                        <a>User List</a>
                      </li>
                    </ul>
                  </div>
                </div>

                <div className="col-sm-12">
                  <div className="card px-3">
                    <div className="card-header px-0 pb-2">
                      <h2 className="text-uppercase text-center">User List</h2>
                      <div id={btnAddContainer}></div>
                    </div>

                    <UserTable users={users} />
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
        topics={[
          "/topic/user-topic/add",
          "/topic/user-topic/delete",
          "/topic/user-topic/edit",
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
          const actionAdd = "user/add";
          const actionDelete = "user/del";
          const actionEdit = "user/edit";

          switch (msg.type) {
            case actionAdd:
              if (msg.status === succeedState) {
                dispatch(addAUser(msg.user));
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
                dispatch(deteleAUser(msg.user.id));
              } else if (msg.status === errorState) {
                console.log(msg.error);
              }
              break;
            case actionEdit:
              console.log(msg);
              if (msg.status === succeedState) {
                dispatch(editAUser(msg.user));
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
