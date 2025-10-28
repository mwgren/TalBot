import React, { Component } from 'react';
import FurhatGUI from 'furhat-gui';
//import { Grid, Row, Col, Button as BootstrapButton } from 'react-bootstrap';
import Button from 'react-bootstrap/Button';



class Screen2 extends Component {
constructor(props) {
    super(props);
}

   resume = () => {
        // Update state to indicate speaking
        this.setState({
          speaking: true,
        });
        // Access the furhat object from props and send an event
        const { furhat } = this.props; // Destructure furhat from props
            furhat.send({
            event_name: 'resume', // Example event name
            data: 'listOne', // Data to be sent with the event
          });
      };

render() {
    return (
        <div>
            <div className="div">
                <div className="div-5"></div>
                <div className="div-6">
                    <Button onClick={this.resume} className="button-3">Fortsätt</Button>
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
                        position: relative; /* Needed for button to be positioned relative to this */
                      }
                      @media (max-width: 991px) {
                        .div {
                          padding: 0 20px;
                        }
                      }
                      .button {
                        position: absolute; /* Positioning the button */
                        top: 20px; /* Adjust this to your desired top padding */
                        right: 20px; /* Align the button to the right */
                        justify-content: center;
                        border-radius: 50px;
                        box-shadow: 0px 4px 5px 0px rgba(0, 0, 0, 0.14),
                          0px 1px 10px 0px rgba(0, 0, 0, 0.12),
                          0px 2px 4px 0px rgba(0, 0, 0, 0.2);
                        background-color: var(--02-secondary-200, #03dac5);
                        width: auto; /* Button width adjusts to content */
                        max-width: 180px; /* Prevents the button from being too wide */
                        font-size: 16px; /* Increased font size */
                        line-height: 114%;
                        display: flex;
                        flex-direction: column;
                        padding: 16px 24px; /* Increased padding for a larger button */
                      }

                      @media (max-width: 991px) {
                        .button {
                          padding: 12px 20px;
                        }
                      }
                      .div-2 {
                        gap: 20px;
                        display: flex;
                      }
                      @media (max-width: 991px) {
                        .div-2 {
                          flex-direction: column;
                          align-items: stretch;
                          gap: 0px;
                        }
                      }
                      .column {
                        display: flex;
                        flex-direction: column;
                        line-height: normal;
                        width: 100%;
                        margin-left: 0px;
                      }
                      @media (max-width: 991px) {
                        .column {
                          width: 100%;
                        }
                      }
                      .div-3 {
                        display: flex;
                        gap: 8px;
                      }
                      .img {
                        aspect-ratio: 0.59;
                        object-fit: auto;
                        object-position: center;
                        width: 23px;
                      }
                      .div-4 {
                        font-family: Roboto, sans-serif;
                        margin: auto 0;
                        font-size: 20px; /* Increased font size for "VÄNTA" */
                      }
                      .div-5 {
                        color: #000;
                        font-feature-settings: "clig" off, "liga" off;
                        margin-top: 176px;
                        font: 40px Roboto, sans-serif;
                      }
                      @media (max-width: 991px) {
                        .div-5 {
                          max-width: 100%;
                          margin-top: 40px;
                        }
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
                      @media (max-width: 991px) {
                        .div-6 {
                          max-width: 100%;
                          flex-wrap: wrap;
                          margin-top: 40px;
                          white-space: initial;
                        }
                      }
                      .button-2 {
                        justify-content: center;
                        align-items: center;
                        border-radius: 50px;
                        box-shadow: 0px 4px 5px 0px rgba(0, 0, 0, 0.14),
                          0px 1px 10px 0px rgba(0, 0, 0, 0.12),
                          0px 2px 4px 0px rgba(0, 0, 0, 0.2);
                        background-color: #fc7272;
                        flex-grow: 1;
                        width: fit-content;
                        min-height: 75px;
                        padding: 12px;
                        font: 24px Roboto, sans-serif;
                      }
                      @media (max-width: 991px) {
                        .button-2 {
                          white-space: initial;
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
                        margin: 0 auto;
                        width: fit-content;
                        padding: 12px;
                        font: 24px Roboto, sans-serif;
                      }
                      @media (max-width: 991px) {
                        .button-3 {
                          white-space: initial;
                          padding: 0 20px;
                        }
                      }
                    `}</style>
        </div>
    );
}

}

export default Screen2;
