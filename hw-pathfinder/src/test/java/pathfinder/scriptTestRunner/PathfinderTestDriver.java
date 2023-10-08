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

package pathfinder.scriptTestRunner;

import graph.Graph;
import pathfinder.Algorithm;
import pathfinder.datastructures.Path;

import java.io.*;
import java.util.*;

/**
 * This class implements a test driver that uses a script file format
 * to test an implementation of Dijkstra's algorithm on a graph.
 */
public class PathfinderTestDriver<V> {

    // Leave this constructor public
    /**
     * String -> Graph: maps the names of graphs to the actual graph
     **/
    private final Map<String, Graph<Double, String>> graphs = new HashMap<>();

    private final PrintWriter output;
    private final BufferedReader input;

    /**
     * @spec.requires r != null && w != null
     * @spec.effects Creates a new PathfinderTestDriver which reads command from
     * {@code r} and writes results to {@code w}
     **/
    public PathfinderTestDriver(Reader r, Writer w) {
        input = new BufferedReader(r);
        output = new PrintWriter(w);
        // TODO: Implement this, reading commands from `r` and writing output to `w`.
        // See GraphTestDriver as an example.
    }

    // Leave this method public
    /**
     * @throws IOException if the input or output sources encounter an IOException
     * @spec.effects Executes the commands read from the input and writes results to the output
     **/
    public void runTests()  throws IOException {
        String inputLine;
        while((inputLine = input.readLine()) != null) {
            if((inputLine.trim().length() == 0) ||
                    (inputLine.charAt(0) == '#')) {
                // echo blank and comment lines
                output.println(inputLine);
            } else {
                // separate the input line on white space
                StringTokenizer st = new StringTokenizer(inputLine);
                if(st.hasMoreTokens()) {
                    String command = st.nextToken();

                    List<String> arguments = new ArrayList<>();
                    while(st.hasMoreTokens()) {
                        arguments.add(st.nextToken());
                    }

                    executeCommand(command, arguments);
                }
            }
            output.flush();
        }
        // TODO: Implement this.
    }

    private void executeCommand(String command, List<String> arguments) {
        try {
            switch(command) {
                case "CreateGraph":
                    createGraph(arguments);
                    break;
                case "AddNode":
                    addNode(arguments);
                    break;
                case "AddEdge":
                    addEdge(arguments);
                    break;
                case "FindPath":
                    FindPath(arguments);
                    break;
                default:
                    output.println("Unrecognized command: " + command);
                    break;
            }
        } catch(Exception e) {
            String formattedCommand = command;
            formattedCommand += arguments.stream().reduce("", (a, b) -> a + " " + b);
            output.println("Exception while running command: " + formattedCommand);
            e.printStackTrace(output);
        }
    }

    private void createGraph(List<String> arguments) {
        if(arguments.size() != 1) {
            throw new CommandException("Bad arguments to CreateGraph: " + arguments);
        }

        String graphName = arguments.get(0);
        createGraph(graphName);
    }

    private void createGraph(String graphName) {
        graphs.put(graphName, new Graph<>());
        output.println("created graph " + graphName);
    }

    private void addNode(List<String> arguments) {
        if(arguments.size() != 2) {
            throw new CommandException("Bad arguments to AddNode: " + arguments);
        }

        String graphName = arguments.get(0);
        String nodeName = arguments.get(1);

        addNode(graphName, nodeName);
    }

    private void addNode(String graphName, String nodeName) {
        Graph<Double, String> graph = graphs.get(graphName);
        graph.insertNode(new Graph.Node<>(nodeName));
        output.println("added node " + nodeName + " to " + graphName);
    }

    private void addEdge(List<String> arguments) {
        if(arguments.size() != 4) {
            throw new CommandException("Bad arguments to AddEdge: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        String childName = arguments.get(2);
        String str = arguments.get(3);
        double edgeLabel = Double.parseDouble(str);


        addEdge(graphName, parentName, childName, edgeLabel);
    }

    private void addEdge(String graphName, String parentName, String childName,
                         Double edgeLabel) {
        Graph<Double, String> graph = graphs.get(graphName);
        String s = String.format("%.3f", edgeLabel);
        graph.insertEdge(new Graph.Node<>(parentName), new Graph.Node<>(childName), edgeLabel);
        output.println("added edge " + s + " from " + parentName + " to "
                + childName + " in " + graphName
        );
    }

    private void FindPath(List<String> arguments) {
        if(arguments.size() != 3) {
            throw new CommandException("Bad arguments to AddNode: " + arguments);
        }

        String graphName = arguments.get(0);
        String startName = arguments.get(1);
        String destName = arguments.get(2);

        FindPath(graphName, startName, destName);
    }

    private void FindPath(String graphName, String startName, String destName) {
        // TODO Insert your code here.

        Graph<Double, String> graph = graphs.get(graphName);
        if (!graph.containsNode(new Graph.Node<String>(startName))) {
            output.println("unknown: " + startName);
        }
        if (!graph.containsNode(new Graph.Node<String>(destName))) {
            output.println("unknown: " + destName);
        } else {
            Path<String> path = Algorithm.dijkstra(graph, startName,
                    destName);
            output.println("path from " + startName+ " to " + destName + ":");
            double count = 0.000;

            if (path != null) {
                Iterator<Path<String>.Segment> i = path.iterator();
                while (i.hasNext()) {
                    Path<String>.Segment a = i.next();
                    output.println(a.getStart() + " to " + a.getEnd()
                            + " with weight " + String.format("%.3f", a.getCost()));
                    count = count + a.getCost();
                }
                output.println("total cost: " + String.format("%.3f", count));
            } else {
                output.println("no path found");
            }
        }
    }

    /**
     * This exception results when the input file cannot be parsed properly
     **/
    static class CommandException extends RuntimeException {

        public CommandException() {
            super();
        }

        public CommandException(String s) {
            super(s);
        }

        public static final long serialVersionUID = 3495;
    }
}
