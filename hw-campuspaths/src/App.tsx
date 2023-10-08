/*
 * Copyright (C) 2022 Kevin Zatloukal and James Wilcox.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Autumn Quarter 2022 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

import React, { Component } from "react";
import Map from "./Map";
import Edge from "./Edge";


// Allows us to write CSS styles inside App.css, any styles will apply to all components inside <App />
import "./App.css";
import MapLine from "./MapLine";
import Path from "./Path";

interface AppState {
    // an arraylist of Edges (as defined by the Edge interface)
    edges: Edge[];  // a list of edges that represents the shortest path
    Buildings: any[]; // list of buildings
    ShortNames: any[] // list of the short names of buildings
    start: string; // start building
    end: string; // end building
}

class App extends Component<{}, AppState> { // <- {} means no props.

    constructor(props: any) {
        super(props);
        this.state = {
            edges: [], Buildings: [], ShortNames: [], start: "PAR", end: "PAR"
        };
        this.printBuildings(); // prints out all the buildings onto the dropdown menu
    }

    printBuildings = async () => {
        try {
            let currBuildings: any = []; // list of buildings
            let currShortNames: any = []; // list of the short names of buildings
            let response = await fetch("http://localhost:4567/building-names");
            if (!response.ok) {
                alert("The status is wrong: " + response.status);
                return; // Don't keep trying to execute if the response is bad.
            }
            // response.json() expects that the server returned a JSON string and will
            // convert it to a JS object for you.
            // If the response doesn't contain valid JSON, it'll throw an exception
            // We know that the /range endpoint returns an ArrayList converted to JSON, which
            // will show up inside JS as an array. So the 'object' variable will contain an array.
            // We can cast this to the appropriate TypeScript type.
            let object = await response.json();
            //
            console.log(object);

            for (let key in object) {
                currBuildings.push(object[key]);
                currShortNames.push(key);
            }

            // set the state of Buildings and ShortNames equal to the data we got from fetch
            this.setState({
                Buildings: currBuildings, ShortNames: currShortNames
            })
        } catch (e) {
            alert("There was an error contacting the server.");
            console.log(e);
        }
    };


    makeRequest = async (st: string, e: string) => {

        this.setState({start: st, end: e});
        console.log(this.state.start + this.state.end);
        try {
            let text = "http://localhost:4567/shortest-path?start=" + this.state.start +
                "&end=" + this.state.end
            let response = await fetch(text);

            if (!response.ok) {
                alert("Oh no! Something went wrong. Here's the status: " + response.status);
                return; // Don't keep trying to execute if the response is bad.
            }
            // response.json() expects that the server returned a JSON string and will
            // convert it to a JS object for you.
            // If the response doesn't contain valid JSON, it'll throw an exception
            // We know that the /range endpoint returns an ArrayList converted to JSON, which
            // will show up inside JS as an array. So the 'object' variable will contain an array.
            // We can cast this to the appropriate TypeScript type.
            let object = (await response.json());

            console.log(object);
            let fetchEdges: any = []; // list of edges

            // parsing the data to create an Edge
            for (let a of object.path) {
                let edge: Edge = {
                    color : "red",
                    x1 : a.start.x,
                    y1 : a.start.y,
                    x2 : a.end.x,
                    y2 : a.end.y,
                }
                fetchEdges.push(edge);
            }
            this.setState({edges: fetchEdges})

        } catch (e) {
            alert("There was an error contacting the server.");
            console.log(e);
        }
    };

    render() {
        return (
            <div>
                <h1 id="app-title">Line Mapper!</h1>
                <div>
                    <Map edges={this.state.edges}/>
                </div>
                <div>
                    <Path
                        onPath={(st: string, e: string) => this.makeRequest(st, e)}
                        buildings ={this.state.Buildings}
                        shortNames = {this.state.ShortNames}
                        onStartChange={
                        (st: string) => this.setState({start: st})}
                        onEndChange={(st: string) => this.setState({end: st})}
                    />
                </div>
            </div>
    );
    }
}

export default App;
