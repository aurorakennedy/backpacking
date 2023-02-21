import React from 'react';
import Header from './Header';
import './homePageStyle.css'
import NavBar from './NavBar';
import TravelRoute from './TravelRoute';

const HomePage = () => {

    return(
        <>
        <NavBar/>

        <div id= 'homePage'>

            <div id='search'>
                <input id='searchBar' type='text' placeholder='Where do you want to travel?' /* onChange={} */ />
            </div>
            
            <div id='reiseruter'>
            <TravelRoute
                        title='Route 1'
                        description='Lorem ipsum dolor sit amet, consectetur adipiscing elit.'
                        duration='5 days'
                        price='$500' /* image={''}      */       />
            <TravelRoute
                        title='Route 2'
                        description='Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium.'
                        duration='7 days'
                        price='$700' /* image={''}   */          />
            </div>


            
            <div id='test'>
                <p>
                "On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."
                </p>


                <p>
                "On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."
                </p>

                <p>
                "On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."
                </p>

            </div>

           

        </div>
        </>
    )


}

export default HomePage;