import React, { useState, useEffect } from "react";
import axios from "axios";
import "./HomePage.css";
import { useNavigate } from "react-router-dom";
import Product from "./Product";
import LogoutHeader from "./LogoutHeader";
import Footer from "./Footer";

function App() {
  const products = [
    { id: 1, name: "Product 1" },
    { id: 2, name: "Product 2" },
    { id: 3, name: "Product 3" },
    { id: 4, name: "Product 4" },
    { id: 5, name: "Product 5" },
    { id: 6, name: "Product 6" },
    { id: 7, name: "Product 7" },
    { id: 1, name: "Product 1" },
    { id: 2, name: "Product 2" },
    { id: 3, name: "Product 3" },
    { id: 4, name: "Product 4" },
    { id: 5, name: "Product 5" },
    { id: 6, name: "Product 6" },
    { id: 7, name: "Product 7" },
    { id: 1, name: "Product 1" },
    { id: 2, name: "Product 2" },
    { id: 3, name: "Product 3" },
    { id: 4, name: "Product 4" },
    { id: 5, name: "Product 5" },
    { id: 6, name: "Product 6" },
    { id: 7, name: "Product 7" },
    { id: 1, name: "Product 1" },
    { id: 2, name: "Product 2" },
    { id: 3, name: "Product 3" },
    { id: 4, name: "Product 4" },
    { id: 5, name: "Product 5" },
    { id: 6, name: "Product 6" },
    { id: 7, name: "Product 7" },
  ];
  const productsPerPage = 6;
  const [currentPage, setCurrentPage] = useState(1);

  const totalPages = Math.ceil(products.length / productsPerPage);
  const pageRange = 5;
  const pageNumbers = [];

  for (let i = 1; i <= Math.min(totalPages, pageRange); i++) {
    pageNumbers.push(i);
  }

  const startIndex = (currentPage - 1) * productsPerPage;
  const endIndex = startIndex + productsPerPage;
  const currentProducts = products.slice(startIndex, endIndex);

  const getProduct = async () => {
    const res = await axios.get();
  };

  useEffect =
    (() => {
      getProduct();
    },
    [currentPage]);

  const goToPage = (page) => {
    setCurrentPage(page);
  };

  return (
    <div className="page">
      <LogoutHeader />
      <main>
        <div className="mainContainer">
          {currentProducts.map((product, idx) => (
            <Product key={product.id} product={product} idx={idx} />
          ))}
        </div>

        <div className="pagination">
          {currentPage !== 1 && (
            <>
              <button className="firstPage" onClick={() => goToPage(1)}>
                <img src="/" alt="" />
              </button>
              <button
                className="prevPage"
                onClick={() => goToPage(currentPage - 1)}
              >
                <img src="" alt="" />
              </button>
            </>
          )}

          {pageNumbers.map((page) => (
            <button
              key={page}
              onClick={() => goToPage(page)}
              className={currentPage === page ? "pageActive" : "pageBtn"}
            >
              {page}
            </button>
          ))}

          {currentPage !== totalPages && (
            <>
              <button
                className="nextPage"
                onClick={() => goToPage(currentPage + 1)}
              ></button>
              <button
                className="lastPage"
                onClick={() => goToPage(totalPages)}
              ></button>
            </>
          )}
        </div>
      </main>
      <Footer />
    </div>
  );
}
export default App;
