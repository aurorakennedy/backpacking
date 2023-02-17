import React from 'react';
//import './header.css'


/* interface Props {
    showHeader: boolean;
  }
  
  const Header: React.FC<Props> = ({ showHeader }) => {
    return showHeader ? (
      <header>
        <h1 id='title'> BP-Advisor Aurora Test</h1>
        <h2 id='subTitle'>Share your backpacking routes</h2>
      </header>
    ) : null;
  };
  
  export default Header; */

  const Header = () => {

    return(
        <div>
            <h1 id='title'> BP-Advisor</h1>
            <h2 id='subTitle'>Share your backpacking routes</h2>
        </div>
    )

}

export default Header;