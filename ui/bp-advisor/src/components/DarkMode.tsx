import styles from './Comment.module.css';
import "./darkMode.css";
//import "./homePageStyle.css";
import { ChangeEventHandler, useState } from "react";

const DarkMode: React.FC = () => {
    const [darkMode, setDarkMode] = useState(false);
  
    const handleToggleDarkMode = () => {
      setDarkMode(!darkMode);
      if (!darkMode) {
        document.getElementById("homePage")?.classList.add("dark-mode");
        document.getElementById("nav")?.classList.add("dark-mode");
        document.getElementById("logo")?.classList.add("dark-mode");


      } else {
        document.getElementById("homePage")?.classList.remove("dark-mode");
        document.getElementById("nav")?.classList.remove("dark-mode");
        document.getElementById("logo")?.classList.remove("dark-mode");


      }
    };
  
    return (
      <div>
        <button onClick={handleToggleDarkMode}>
          {darkMode ? "Light Mode" : "Dark Mode"}
        </button>
      </div>
    );
  };

  export default DarkMode;

// import React, { useState } from "react";

// const DarkMode: React.FC = () => {
//   const [darkMode, setDarkMode] = useState(false);

//   const handleToggleDarkMode = () => {
//     setDarkMode(!darkMode);
//   };

//   return (
//     <div className={`homePage ${darkMode ? "dark-mode" : ""}`}>
//       <button onClick={handleToggleDarkMode}>
//         {darkMode ? "Light Mode" : "Dark Mode"}
//       </button>
//     </div>
//   );
// };

// export default DarkMode;

/* // 1
const setDark = () => {

  // 2
  localStorage.setItem("theme", "dark"); //Lagerer settingen 

  // 3
  document.documentElement.setAttribute("data-theme", "dark");
};

const setLight = () => {
  localStorage.setItem("theme", "light");
  document.documentElement.setAttribute("data-theme", "light");
};

// 4
const storedTheme = localStorage.getItem("theme");

const prefersDark =
  window.matchMedia &&
  window.matchMedia("(prefers-color-scheme: dark)").matches;

const defaultDark =
  storedTheme === "dark" || (storedTheme === null && prefersDark);

if (defaultDark) {
  setDark();
}

// 5
const toggleTheme: ChangeEventHandler<HTMLInputElement> = (e) => {
  if (e.target.checked) {
    setDark();
  } else {
    setLight();
  }
};

const DarkMode = () => {
  return (
    <div className="toggle-theme-wrapper">
      <span>Light</span>
      <label className="toggle-theme" htmlFor="checkbox">
        <input
          type="checkbox"
          id="checkbox"

          onClick={ () => toggleTheme}
          defaultChecked={defaultDark}
        />
        <div className="slider round"></div>
      </label>
      <span>Dark</span>
    </div>
  );
};

export default DarkMode; */

// export default function DarkMode(): JSX.Element {
//     const [darkMode, setDarkMode] = useState<boolean>(false);
  
//     const handleToggleDarkMode = (): void => {
//       setDarkMode(!darkMode);
//       localStorage.setItem("darkMode", JSON.stringify(!darkMode));
//       window.dispatchEvent(new Event("storage"));
//     };
  
//     return (
//       <div
//         className = "toggel"
//         onClick={() => handleToggleDarkMode()}
//       >
//         {darkMode ? "Light Mode" : "Dark Mode"}
//       </div>
//     );
//   }

// import { useState, useEffect } from "react";

// type DarkModeProps = {
//   defaultDark?: boolean;
// };

// const DarkMode = ({ defaultDark = false }: DarkModeProps) => {
//   const [darkMode, setDarkMode] = useState<boolean>(
//     defaultDark ?? Boolean(localStorage.getItem("darkMode"))
//   );

//   useEffect(() => {
//     document.body.classList.toggle("dark-mode", darkMode);
//     localStorage.setItem("darkMode", String(darkMode));
//   }, [darkMode]);

//   const toggleDarkMode = () => {
//     setDarkMode(!darkMode);
//   };

//   return (
//     <div className="dark-mode-toggle" onClick={toggleDarkMode}>
//       {darkMode ? "Light Mode" : "Dark Mode"}
//     </div>
//   );
// };

// export default DarkMode;


