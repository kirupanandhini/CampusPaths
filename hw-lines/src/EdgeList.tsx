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

import React, {Component} from 'react';
import MapLine from "./MapLine";
import {Browser} from "leaflet";
import Edge from "./Edge";

interface EdgeListProps {
    onDraw(edges: string[][]): void;
}

interface EdgeListState {
    edgeState: string;
    error: string;
}

/**
 * A text field that allows the user to enter the list of edges.
 * Also contains the buttons that the user will use to interact with the app.
 */
class EdgeList extends Component<EdgeListProps, EdgeListState> {
    constructor(props: any) {
        super(props);
        this.state = {
            edgeState: "",
            error: ""
        };
    }
    render() {
        return (
            <div id="edge-list">
                Edges <br/>
                <div>{this.state.error}</div>
                <textarea
                    rows={5}
                    cols={30}
                    onChange={evt => this.setEdges(evt.target.value)}
                /> <br/>
                <button onClick={() =>
                    this.onDrawEdgeList()}>Draw</button>
                <button onClick={() => this.props.onDraw([])}>Clear</button>
            </div>
        );
    }

    setEdges(value : string) {
        console.log(value);
        this.setState({edgeState: value});
    }

    onDrawEdgeList() {
        console.log(this.state.edgeState);
        this.setState({error: ""});  // resets error state
        let validInput : boolean = true;  // keeps track of whether the text input is valid
        if (this.state.edgeState !== null) {
            let edgeList: any = []
            let lines: string[] = this.state.edgeState.split("\n");
            console.log(lines);
            for (let line in lines) {
                let info: string[] = lines[line].trim().split(" ");
                if (info.length !== 5) {
                    validInput = false;
                    this.setState({error: "Error: not in the proper format" +
                            ", which is x1 y2 x2 y2 COLOR"})
                } else {
                    for (let i = 0; i < 4; i++) {
                        if (info[i] === "") {
                            this.setState({error: "Error: not in the proper format" +
                                    ", which is x1 y2 x2 y2 COLOR"})
                            validInput = false;
                        }
                        if (Number.isNaN(Number(info[i]))) {
                            this.setState({error: "Error: at least one of the coordinates" +
                                    " is not a number"})
                            validInput = false;
                        }
                        if (parseInt(info[i]) < 0 || parseInt(info[i]) > 4000) {
                            this.setState({error: "Error: coordinates must be between 0 and " +
                                    "4000 (inclusive)"})
                            validInput = false;
                        }
                    }
                }
                if (validInput) {
                    let edges: Edge = {
                        color : info[4],
                        x1 : parseInt(info[0]),
                        y1 : parseInt(info[1]),
                        x2 : parseInt(info[2]),
                        y2 : parseInt(info[3]),
                    }
                    edgeList.push(edges);
                }
            }
            if (validInput) {
                this.props.onDraw(edgeList); // where edgeList is an arrayList of edges
            }
        }
    }
}

export default EdgeList;
