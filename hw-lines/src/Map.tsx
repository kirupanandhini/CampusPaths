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

import { LatLngExpression } from "leaflet";
import React, { Component } from "react";
import { MapContainer, TileLayer } from "react-leaflet";
import "leaflet/dist/leaflet.css";
import MapLine from "./MapLine";
import { UW_LATITUDE_CENTER, UW_LONGITUDE_CENTER } from "./Constants";
import Edge from "./Edge";

// This defines the location of the map. These are the coordinates of the UW Seattle campus
const position: LatLngExpression = [UW_LATITUDE_CENTER, UW_LONGITUDE_CENTER];

interface MapProps {
    // an arraylist of edges as defined by the Edge interface
    edges: Edge[]
}

interface MapState {}

class Map extends Component<MapProps, MapState> {

  render() {
      // lines represents an array of Maplines
      let lines: any = [];
      for (let i = 0; i < this.props.edges.length; i++) {
          lines.push(<MapLine
              color = {this.props.edges[i].color}
              x1 = {this.props.edges[i].x1}
              y1 = {this.props.edges[i].y1}
              x2 = {this.props.edges[i].x2}
              y2 = {this.props.edges[i].y2}
          />)
      }
    return (
      <div id="map">
        <MapContainer
          center={position}
          zoom={15}
          scrollWheelZoom={false}
        >
          <TileLayer
            attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
            url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
          />
          {lines}
        </MapContainer>
      </div>
    );
  }
}

export default Map;
