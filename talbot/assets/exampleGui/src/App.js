import React, { Component } from 'react';
import FurhatGUI from 'furhat-gui';
import Screen0 from './Screen0';
import Screen1 from './Screen1';
import Screen2 from './Screen2';
import Screen3 from './Screen3';

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      speaking: false,
      buttons: [],
      showScreen: 0,
      screenText: null
    };
    this.furhat = null;
  }

  setupSubscriptions() {
    this.furhat.subscribe('furhatos.app.furgui.DataDelivery', (data) => {
    console.log('Data received:', data);
      this.setState({
        ...this.state,
         showScreen: data.screen,
         screenText: data.text
      });
    });

    this.furhat.subscribe('SpeechDone', () => {
      this.setState({
        speaking: false,
      });
    });
  }

  componentDidMount() {
    FurhatGUI()
      .then((connection) => {
        this.furhat = connection;
        console.log("OBJECT"+this.furhat)
        this.setupSubscriptions();
      })
      .catch(console.error);
  }

  clickButton = (button) => {
    this.setState({
      speaking: true,
    });
    this.furhat.send({
      event_name: 'ClickButton',
      data: button,
    });
  };


help = () => {
         //alert("HELLO")
         this.setState({
              speaking: true,
            });
            this.furhat.send({
              event_name: 'HelpButton',
              data: "calling for help",
            });
          };


  variableSet = (variable, value) => {
    this.setState({
      speaking: true,
    });
    this.furhat.send({
      event_name: 'VariableSet',
      data: {
        variable,
        value,
      },
    });
  };

  render() {
      const {showScreen} = this.state;
      console.log(this.state.showScreen)

      if (showScreen === 0) {
        return (
          <div>
          <Screen0 furhat={this.furhat}/>
          </div>
        );
      }

      if (showScreen === 1) {
        return (
          <div>
            <Screen1 furhat={this.furhat}/>
          </div>
        );
      }

      if (showScreen=== 2) {
        return (
          <div>
            <Screen2 furhat={this.furhat}/>
          </div>
        );
      }

      if (showScreen=== 3) {
              return (
                <div>
                  <Screen3 furhat={this.furhat}/>
                </div>
              );
            }
            if (showScreen=== 4) {
                          return (
                            <div>
                              <Screen4 furhat={this.furhat}/>
                            </div>
                          );
                        }
            if (showScreen=== 5) {
                                      return (
                                        <div>
                                          <Screen5 furhat={this.furhat}/>
                                        </div>
                                      );
                                    }
            if (showScreen=== 6) {
                                                  return (
                                                    <div>
                                                      <Screen6 screenText={this.state.screenText} furhat={this.furhat}/>
                                                    </div>
                                                  );
                                                }

      return (
        <div>
        </div>
      );
    }
  }

  export default App;
