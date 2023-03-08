import { useEffect, useState } from "react";
import TravelRoute from "./TravelRoute"
import "./itineraryListBoxStyle.css"
import ItineraryDelete from "./DeleteItinerary";



interface itinerarySummaryInfo {
    title: string;
    author: string;
    duration: string;
    price: string;
    description: string;
}

const mockItinerary1: itinerarySummaryInfo = {
    title: 'Backpacking South America',
    author: 'Jarl28',
    duration: '40 days',
    price: '$ 3000',
    description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.'
}

const mockItinerary2: itinerarySummaryInfo = {
    title: 'Interrail in Europe',
    author: 'Aurora24',
    duration: '30 days',
    price: '$ 2900',
    description: 'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium.'
}

const mockItinerary3: itinerarySummaryInfo = {
    title: 'Bikepacking in Asia',
    author: 'Tobias32',
    duration: '50 days',
    price: '$ 3500',
    description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.'
}

const mockItinerary4: itinerarySummaryInfo = {
    title: 'USA coast to coast',
    author: 'Simen20',
    duration: '45 days',
    price: '$ 3600',
    description: 'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium.'
}




const ItineraryListBox = () => {

    const [itineraryBoxExpanded, setitineraryBoxExpanded] = useState<Boolean>(false);

    const handleExpantion = () => {
        setitineraryBoxExpanded(!itineraryBoxExpanded);
    };

    /* useEffect(() => {
        setDestinations(["Peru", "Chile", "Argentina"]);
    }, []); */

    /* const destinationsHTMLElement: HTMLDivElement = document.getElementById('itineraryDestinationBox') as HTMLDivElement;

    const setDestinations = (destinations: string[]) => {
        destinations.forEach(destination => {
            let p = document.createElement('p');
            p.textContent = destination as string;
            destinationsHTMLElement.appendChild(p);
        });
    } */

    

    return (
        <>
            <div id='travelRouteDiv'>

                <div onClick={handleExpantion} className="itinerarySummaryDiv">
                    <TravelRoute
                        title={mockItinerary1.title}
                        description={mockItinerary1.description}
                        duration={mockItinerary1.duration}
                        price={mockItinerary1.price} /* image={''}      */ />
                </div>

                <div onClick={handleExpantion} className="itinerarySummaryDiv">
                    <TravelRoute
                        title={mockItinerary2.title}
                        description={mockItinerary2.description}
                        duration={mockItinerary2.duration}
                        price={mockItinerary2.price} /* image={''}      */ />
                </div>
                <div onClick={handleExpantion} className="itinerarySummaryDiv">
                    <TravelRoute
                        title={mockItinerary3.title}
                        description={mockItinerary3.description}
                        duration={mockItinerary3.duration}
                        price={mockItinerary3.price} /* image={''}      */ />
                </div>

                <div onClick={handleExpantion} className="itinerarySummaryDiv">
                    <TravelRoute
                        title={mockItinerary4.title}
                        description={mockItinerary4.description}
                        duration={mockItinerary4.duration}
                        price={mockItinerary4.price} /* image={''}      */ />
                </div>
            </div>
            {!itineraryBoxExpanded ? (<></>)
                : (<><div id="itineraryBox">
                    <div id="itineraryDestinationBox">
                        <p id='itineraryDestinationBoxTitle'> Destinations: </p>
                        <p>Chile</p>
                        <p>Argentina</p>
                        <p>Peru</p>
                    </div>
                    <div id="itineraryColumnFlexBox">
                        <h2 id='itineraryBoxTitle'> {'Backpacking South America'}</h2>
                        <div id='itineraryDetailsFlexBox'>
                            <p className='itineraryDetailElement'> Author: {"Jarl28"}</p>
                            <p className='itineraryDetailElement'> Duration: {"40"}</p>
                            <p className='itineraryDetailElement'> Cost: {"$ 3000"}</p>
                        </div>
                        <p id='itineraryBoxDescription'>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ipsum dolor sit amet consectetur adipiscing.
                            <br></br>
                            <br></br>
                            Nec sagittis aliquam malesuada bibendum arcu vitae elementum. Varius duis at consectetur lorem donec massa sapien. Commodo viverra maecenas accumsan lacus vel facilisis. Aliquet eget sit amet tellus. Praesent semper feugiat nibh sed pulvinar proin gravida hendrerit lectus. Condimentum id venenatis a condimentum vitae sapien pellentesque habitant morbi. Tellus pellentesque eu tincidunt tortor aliquam nulla. Scelerisque mauris pellentesque pulvinar pellentesque habitant. Pretium nibh ipsum consequat nisl. Egestas tellus rutrum tellus pellentesque eu tincidunt tortor aliquam nulla. Adipiscing vitae proin sagittis nisl rhoncus.
                            <br></br>
                            <br></br>

                            Felis donec et odio pellentesque diam volutpat commodo sed. Dictum fusce ut placerat orci nulla pellentesque dignissim enim sit. Consectetur adipiscing elit pellentesque habitant morbi tristique senectus. Cras adipiscing enim eu turpis egestas. Faucibus in ornare quam viverra orci sagittis eu volutpat. Cursus in hac habitasse platea. Odio pellentesque diam volutpat commodo sed egestas egestas fringilla. Mauris sit amet massa vitae tortor. Vitae congue mauris rhoncus aenean vel elit scelerisque mauris. Suspendisse in est ante in nibh. Montes nascetur ridiculus mus mauris vitae ultricies. Morbi tincidunt augue interdum velit euismod in pellentesque. Eu lobortis elementum nibh tellus. Amet consectetur adipiscing elit pellentesque habitant morbi. Nunc sed augue lacus viverra vitae congue eu consequat ac. Viverra suspendisse potenti nullam ac tortor vitae purus faucibus.
                        </p>
                        
                        
                    </div>
                    <p onClick={handleExpantion} id='itineraryBoxCloseButton'> Close </p>

                    <ItineraryDelete itinerary={} onDelete={function (): void {
                        throw new Error("Function not implemented.");
                    } }/>
                    
                    
                </div></>)}
        </>
    )

}

export default ItineraryListBox