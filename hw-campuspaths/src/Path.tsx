import React, {Component} from 'react';
import MapLine from "./MapLine";
import {Browser} from "leaflet";
import Edge from "./Edge";

// state --> start and end short Names

// prop --> On click, sends shortest Path (list of edges) to App

interface PathState {
    start: string; // building at the start of the path
    end: string;   // building at the end of the path
}

interface PathProps {
    onPath(start: string, end: string): void; // passes start and end building info to App
    onStartChange(start: string): void; // passes start building to App
    onEndChange(start: string): void; // passes end building to App on change
    buildings: any[]; // long names of buildings
    shortNames: any[]; // short names of buildings
}

class Path extends Component<PathProps,PathState> {
    constructor(props: any) {
        super(props);
        this.state = {
            start: "PAR", // starts and ends at Parrington Hall (no path)
            end: "PAR",
        };
    }

    render() {
        // creating the dropdown menu from buildings and shortNames props
        let lines: any = [];
        for (let i = 0; i < this.props.shortNames.length; i++) {
            lines.push(<option value = {this.props.shortNames[i]}>
                {this.props.buildings[i]}
            </option>)
        }

        return (
            <div id="path">
                Start Building <br/>
                <select name="building" id="buildings"
                        onChange={evt => this.saveStart(evt.target.value)}>
                    {lines}
                </select>
                <br/>
                End Building <br/>
                <select name="building" id="buildings"
                        onChange={evt => this.saveEnd(evt.target.value)}>
                    {lines}
                </select>
                <br/>
                <button onClick={() => this.savePoints(this.state.start, this.state.end)}>Find Shortest Path</button>
                <button onClick={() => window.location.reload()}>Clear</button>
                <br/>
            </div>
        );
    }

    saveStart(st: string) {
        console.log(st);
        this.setState({start: st});
        console.log("path change start: " + this.state.start);
        this.props.onStartChange(st);
    }

    saveEnd(e: string) {
        this.setState({end: e});
        this.props.onEndChange(e);
    }

    savePoints(start: string, end: string) {
        console.log("Path Start:" + start);
        console.log("Path end: " + end);
        this.props.onPath(start, end);
    }
}

export default Path;