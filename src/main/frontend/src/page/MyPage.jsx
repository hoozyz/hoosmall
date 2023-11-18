import React, { useState, useEffect, useRef } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "./HomePage.css";
import List from "./List";
import LoginHeader from "./LoginHeader";
import Footer from "./Footer";
import Pagination from "./Pagination";

function MyPage() {
  const [currentPage, setCurrentPage] = useState(1);
  const [pageInfo, setPageInfo] = useState([]);
  const [lists, setLists] = useState([]);

  const childRef = useRef();
  let token = JSON.parse(localStorage.getItem("token"));

  const checkExpire = () => {
    childRef.current.checkExpire();
  };

  const getProduct = async () => {
    checkExpire();

    const res = await axios
      .get(`/pay/mypage/${currentPage}`, {
        headers: {
          Authorization: "Bearer " + token.accessToken,
        },
      })
      .then((res) => {
        const data = JSON.parse(JSON.stringify(res.data));
        if (data.message) {
          // 토큰 에러
          alert("" + data.message);
        } else {
          setLists(data.list); // 응답 중에서 상품들 리스트
          setPageInfo(data.pageInfo); // 응답 중 페이지에 대한 정보
        }
      })
      .catch((error) => {
        alert(error);
      });
  };

  useEffect(() => {
    getProduct();
  }, [currentPage]);

  const goToPage = (page) => {
    setCurrentPage(page);
  };

  return (
    <div className="page">
      <LoginHeader token={token} ref={childRef} />
      <main>
        <div className="mypageTitle">
          {lists != null ? "나의 결제내역" : "결제내역이 없습니다."}
        </div>
        <div className="mainContainer">
          {lists.map((list, idx) => (
            <List
              key={list.id}
              list={list}
              idx={idx}
              token={token}
              checkExpire={checkExpire}
            />
          ))}
        </div>

        <Pagination
          currentPage={currentPage}
          start={pageInfo.startPage}
          end={pageInfo.endPage}
          max={pageInfo.maxPage}
          goToPage={goToPage}
        />
      </main>
      <Footer />
    </div>
  );
}
export default MyPage;
