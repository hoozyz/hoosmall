import React from "react";
import "./App.css";
import { Routes, Route, BrowserRouter } from "react-router-dom";
import HomePage from "./page/HomePage";
import DetailPage from "./page/DetailPage";
import RegisterPage from "./page/RegisterPage";
import LoginPage from "./page/LoginPage";
import SuccessPage from "./page/SuccessPage";
import CancelPage from "./page/CancelPage";
import FailPage from "./page/FailPage";
import MyPage from "./page/MyPage";
import MyCart from "./page/MyCart";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<HomePage />}></Route>
        <Route path="/detail/:pId" element={<DetailPage />}></Route>
        <Route path="/mypage" element={<MyPage />}></Route>
        <Route path="/cart" element={<MyCart />}></Route>
        <Route path="/register" element={<RegisterPage />}></Route>
        <Route path="/login" element={<LoginPage />}></Route>
        <Route path="/pay/success" element={<SuccessPage />}></Route>
        <Route path="/pay/cancel" element={<CancelPage />}></Route>
        <Route path="/pay/fail" element={<FailPage />}></Route>
      </Routes>
    </BrowserRouter>
  );
}
export default App;
