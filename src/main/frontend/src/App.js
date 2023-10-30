import React from "react";
import "./App.css";
import { Routes, Route, BrowserRouter } from "react-router-dom";
import HomePage from "./page/HomePage";
import DetailPage from "./page/DetailPage";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<HomePage />}></Route>
        <Route path="/detail" element={<DetailPage />}></Route>
        <Route path="/register" element={<HomePage />}></Route>
        <Route path="/login" element={<HomePage />}></Route>
        <Route path="/pay/success" element={<HomePage />}></Route>
        <Route path="/pay/successCancel" element={<HomePage />}></Route>
        <Route path="/pay/fail" element={<HomePage />}></Route>
      </Routes>
    </BrowserRouter>
  );
}
export default App;
