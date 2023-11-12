import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "./HomePage.css";
import List from "./List";
import LoginHeader from "./LoginHeader";
import Footer from "./Footer";

function MyPage() {
  const [currentPage, setCurrentPage] = useState(1);
  const [pageInfo, setPageInfo] = useState([]);
  const [lists, setLists] = useState([]);
  const [pages, setPages] = useState([]);

  const getProduct = async () => {
    const res = await axios.get("/pay/mypage/" + currentPage);
    const data = res.data;
    setLists(data.list); // 응답 중에서 상품들 리스트
    setPageInfo(data.pageInfo); // 응답 중 페이지에 대한 정보
    console.log(data.pageInfo);
    const pageArr = [];
    // 현재 페이지의 시작페이지부터 마지막 페이지 까지 페이지 번호 넣기
    for (let i = data.pageInfo.startPage; i <= data.pageInfo.lastPage; i++) {
      pageArr.push(i);
    }
    setPages(pageArr);
  };

  useEffect(() => {
    getProduct();
  }, [currentPage]);

  const goToPage = (page) => {
    setCurrentPage(page);
  };

  return (
    <div className="page">
      <LoginHeader />
      <main>
        <div className="mypageTitle">{lists != null ? "나의 결제내역" : "결제내역이 없습니다."}</div>
        <div className="mainContainer">
          {lists.map((list, idx) => (
            <List key={list.id} list={list} idx={idx} />
          ))}
        </div>

        <div className="pagination">
          {currentPage !== 1 && (
            <>
              <button
                className="firstPage"
                onClick={() => goToPage(1)}
              ></button>
              <button
                className="prevPage"
                onClick={() => goToPage(currentPage - 1)}
              ></button>
            </>
          )}

          {pages.map((page) => (
            <button
              key={page}
              onClick={() => goToPage(page)}
              className={currentPage === page ? "pageActive" : "pageBtn"}
            >
              {page}
            </button>
          ))}

          {currentPage !== pageInfo.maxPage && (
            <>
              <button
                className="nextPage"
                onClick={() => goToPage(currentPage + 1)}
              ></button>
              <button
                className="lastPage"
                onClick={() => goToPage(pageInfo.maxPage)}
              ></button>
            </>
          )}
        </div>
      </main>
      <Footer />
    </div>
  );
}
export default MyPage;
