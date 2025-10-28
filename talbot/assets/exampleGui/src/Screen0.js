import React, { Component } from 'react';
import FurhatGUI from 'furhat-gui';
import Button from 'react-bootstrap/Button';

class Screen0 extends Component {
constructor(props) {
    super(props);
}

 wait = () => {
     // Update state to indicate speaking
     this.setState({
       speaking: true,
     });
     // Access the furhat object from props and send an event
     const { furhat } = this.props; // Destructure furhat from props
         furhat.send({
         event_name: 'WaitButton', // Example event name
         data: 'calling for help', // Data to be sent with the event
       });
   };

   listOne = () => {
        // Update state to indicate speaking
        this.setState({
          speaking: true,
        });
        // Access the furhat object from props and send an event
        const { furhat } = this.props; // Destructure furhat from props
            furhat.send({
            event_name: 'listOne', // Example event name
            data: 'listOne', // Data to be sent with the event
          });
      };

   listTwo = () => {
           // Update state to indicate speaking
           this.setState({
             speaking: true,
           });
           // Access the furhat object from props and send an event
           const { furhat } = this.props; // Destructure furhat from props
               furhat.send({
               event_name: 'listTwo', // Example event name
               data: 'listTwo', // Data to be sent with the event
             });
         };

   listThree = () => {
           // Update state to indicate speaking
           this.setState({
             speaking: true,
           });
           // Access the furhat object from props and send an event
           const { furhat } = this.props; // Destructure furhat from props
               furhat.send({
               event_name: 'listThree', // Example event name
               data: 'listThree', // Data to be sent with the event
             });
         };

    listFour = () => {
              // Update state to indicate speaking
              this.setState({
                speaking: true,
              });
              // Access the furhat object from props and send an event
              const { furhat } = this.props; // Destructure furhat from props
                  furhat.send({
                  event_name: 'listFour', // Example event name
                  data: 'listFour', // Data to be sent with the event
                });
            };

       checkIntro = () => {
                 // Update state to indicate speaking
                 this.setState({
                   speaking: true,
                 });
                 // Access the furhat object from props and send an event
                 const { furhat } = this.props; // Destructure furhat from props
                     furhat.send({
                     event_name: 'checkIntro', // Example event name
                     data: 'checkIntro', // Data to be sent with the event
                   });
               };

       iceBreaker = () => {
                     // Update state to indicate speaking
                     this.setState({
                       speaking: true,
                     });
                     // Access the furhat object from props and send an event
                     const { furhat } = this.props; // Destructure furhat from props
                         furhat.send({
                         event_name: 'iceBreaker', // Example event name
                         data: 'iceBreaker', // Data to be sent with the event
                       });
                   };



      render() {
          return (
              <div>
                  <div className="div">
                      <div className="div-5">Välj listan för alias</div>
                      <div className="div-6">
                          <label className="checkbox-label">
                              <input
                                  type="checkbox"
                                  onClick={this.checkIntro}
                                  className="checkbox-3"
                              />
                              <span className="checkbox-text">Long Intro</span>
                          </label>
                          <Button onClick={this.listOne} className="button-3">Lista 1</Button>
                          <Button onClick={this.listTwo} className="button-3">Lista 2</Button>
                          <Button onClick={this.listThree} className="button-3">Lista 3</Button>
                          <Button onClick={this.listFour} className="button-3">Lista 4</Button>
                          <Button onClick={this.iceBreaker} className="button-3">Breaker</Button>
                      </div>
                  </div>
                  <style jsx>{`
                      .div {
                          background-color: #cbe5f9;
                          display: flex;
                          flex-direction: column;
                          align-items: center;
                          color: var(--00-on-surface-high-emphasis, rgba(0, 0, 0, 0.87));
                          font-weight: 500;
                          text-align: center;
                          text-transform: uppercase;
                          letter-spacing: 1.25px;
                          padding: 36px 28px 80px 80px;
                          position: relative;
                      }
                      @media (max-width: 991px) {
                          .div {
                              padding: 0 20px;
                          }
                      }
                      .button-3 {
                          justify-content: center;
                          align-items: center;
                          border-radius: 50px;
                          box-shadow: 0px 4px 5px 0px rgba(0, 0, 0, 0.14),
                              0px 1px 10px 0px rgba(0, 0, 0, 0.12),
                              0px 2px 4px 0px rgba(0, 0, 0, 0.2);
                          background-color: var(--02-secondary-200, #03dac5);
                          flex-grow: 1;
                          width: fit-content;
                          padding: 12px;
                          font: 24px Roboto, sans-serif;
                      }
                      .div-5 {
                          color: #000;
                          font-feature-settings: "clig" off, "liga" off;
                          margin-top: 176px;
                          font: 40px Roboto, sans-serif;
                      }
                      .div-6 {
                          display: flex;
                          margin-top: 91px;
                          width: 100%;
                          max-width: 855px;
                          gap: 20px;
                          font-size: 32px;
                          white-space: nowrap;
                          line-height: 50%;
                      }
                      .checkbox-label {
                          display: flex;
                          align-items: center;
                          gap: 10px;
                          font-size: 24px;
                          margin-bottom: 20px;
                          margin-top: 20px; /* Added margin to move it down slightly */
                      }
                      .checkbox-3 {
                          width: 30px;
                          height: 30px;
                      }
                      .checkbox-text {
                          display: inline-block;
                      }
                  `}</style>
              </div>
          );
      }
  }

  export default Screen0;


