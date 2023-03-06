import React, { useState } from "react";

const LikeButton: React.FC = () => {
  const [liked, setLiked] = useState(false);
  const [bgColor, setBgColor] = useState("white");

  const handleClick = () => {
    setLiked(!liked);
    setBgColor(liked ? "lightgray" : "pink");

    setTimeout(() => {
      setBgColor("white");
    }, 500);
  };

  const buttonStyle = {
    backgroundColor: bgColor,
    border: "1px solid grey",
    borderRadius: "5px",
    padding: "5px 10px",
    fontSize: "16px",
    cursor: "pointer",
    outline: "none",
    boxShadow: "none",
    transition: "all 0.3s ease-in-out",
    transform: liked ? "scale(1.1)" : "none",
  };

  return (
    <button 
      onClick={handleClick} 
      style={buttonStyle}
    >
      {liked ? "❤️Liked" : "🤍Like"}
    </button>
  );
};

export default LikeButton;
