import React, { useState, useEffect } from "react";

const Pagination = ({ currentPage, start, end, max, goToPage }) => {
  let pages = [];
  for (let i = start; i <= end; i++) {
    pages.push(i);
  }

  const goPage = (page) => {
    goToPage(page);
  };

  return (
    <div className="pagination">
      {currentPage !== 1 && (
        <>
          <button className="firstPage" onClick={() => goPage(1)}></button>
          <button
            className="prevPage"
            onClick={() => goPage(currentPage - 1)}
          ></button>
        </>
      )}

      {pages.map((page) => {
        return (
          <button
            key={page}
            onClick={() => goPage(page)}
            className={currentPage === page ? "pageActive" : "pageBtn"}
          >
            {page}
          </button>
        );
      })}

      {currentPage !== max && (
        <>
          <button
            className="nextPage"
            onClick={() => goPage(currentPage + 1)}
          ></button>
          <button className="lastPage" onClick={() => goPage(max)}></button>
        </>
      )}
    </div>
  );
};

export default Pagination;
