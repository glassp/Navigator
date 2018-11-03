<?php
/**
 * Created by PhpStorm.
 * User: otakupasi
 * Date: 11/1/18
 * Time: 2:25 PM
 */

namespace Navigator;

/**
 * Class Graph
 * @package Navigator
 */
class Graph
{
    /**
     * @var array which stores the destinations of the node with an offset
     */
    private $edges = array();
    /**
     * @var array which store the offset(edges) for the node
     */
    private $offset = array();
    /**
     * @var array which stores the weight for the edges
     */
    private $weight = array();

    /**
     * @var array which stores the latitude of the nodes
     */
    private $latitude = array();

    /**
     * @var array which stores the longitude of the nodes
     */
    private $longitude = array();

    /**
     * @var array which stores the distances calculated by dijkstra
     */
    private $distance = array();

    /**
     * Adds a new Edge and moves indices as well as edges as needed.
     *
     * @param int $start the start node
     * @param int $dest the destination node
     * @param int $weight the weight for the node
     */

    public function addEdge(int $start, int $dest, int $weight)
    {
        //if Node is has offset
        if ($this->hasOutgoingEdges($start)) {
            if ($this->hasOutgoingEdges($start + 1)) {
                //CASE: BETWEEN TWO NODES
                $index = $this->getOffset($start + 1);
                //move edges right by 1
                for ($i = count($this->edges) - 1; $i >= $index; $i--) {
                    $this->edges[$i + 1] = $this->edges[$i];
                }
                //increase offset by 1
                for ($i = $start + 1; $i < count($this->offset); $i++) {
                    $this->setOffset($i, $this->getOffset($i) + 1);
                }
                //add the edge
                $this->edges[$index] = $dest;
                $this->weight[$this->getEdge($start, $dest)] = $weight;
            } else {
                //CASE: NO NODE WITH HIGHER INDEX
                $this->edges[count($this->edges)] = $dest;
                $this->weight[$this->getEdge($start, $dest)] = $weight;
            }
        } else {
            //CASE: NO OFFSET YET
            $this->setOffset($start, count($this->edges));
            $this->edges[count($this->edges)] = $dest;
            $this->weight[$this->getEdge($start, $dest)] = $weight;
        }
    }

    /**
     * Checks if node has outgoing edges
     *
     * @param int $node the node
     * @return bool returns true if [$node] has outgoing edges
     */
    public function hasOutgoingEdges(int $node)
    {

        return isset($this->offset[$node]);
    }

    /**
     * gets the offset for the node
     *
     * @param int $node the node
     * @return int returns the offset or -1
     */
    public function getOffset(int $node)
    {
        if (!$this->hasOutgoingEdges($node)) return -1;
        return $this->offset[$node];
    }

    /**
     * sets the offset for the node
     *
     * @param int $node the node
     * @param int $offset the offset for the node
     */
    public function setOffset(int $node, int $offset)
    {
        $this->offset[$node] = $offset;
    }

    /**
     * @param int $start the start node
     * @param int $dest the destination node
     * @return bool|int returns the edge index or false
     */
    public function getEdge(int $start, int $dest)
    {
        //gets all edges starting from start
        $from = $this->getOffset($start);
        $to = $this->getOffset($start + 1);
        //if no such edges return
        if ($from == -1) return false;
        if ($to == -1) $to = count($this->edges);
        //for all those edges search if destination is same
        for ($i = $from; $i < $to; $i++) {
            //return edge index if found
            if ($this->edges[$i] == $dest) return $i;
        }
        //if nothing found return
        return false;
    }

    /**
     * Checks if the graph has edges
     *
     * @return bool returns true if graph has edges
     */
    public function hasEdge()
    {
        return !empty($this->edges);
    }

    /**
     * Returns the next node weight-wise
     *
     * @param int $node the node
     * @return int the next node or -1
     */
    public function nextNode(int $node)
    {
        //running infinit time
        $minVal = INF;
        $minIndex = -1;
        $offset = $this->getOffset($node);
        if ($offset == -1) return -1;
        $elem = $this->countOutgoingEdges($node);
        if ($elem < 1) return -1;
        for ($i = 0; $i < $elem; $i++) {
            $var = $this->getWeight($node, $this->edges[$offset + $i]);
            if ($var < $minVal) {
                $minVal = $var;
                $minIndex = $i;
            }
        }
        return $this->edges[$offset + $minIndex];
    }

    /**
     * Counts the outgoing edges
     *
     * @param int $node the node
     * @return int returns the number of outgoing edges
     */
    public function countOutgoingEdges(int $node)
    {
        if (!$this->hasOutgoingEdges($node)) return 0;
        if ($this->getOffset($node + 1) == -1)
            return count($this->edges) - $this->getOffset($node);
        else
            return $this->getOffset($node + 1) - $this->getOffset($node);
    }

    /**
     * returns the weight of the edge
     *
     * @param int $start the start node
     * @param int $dest the destination node
     * @return int returns the weight of the edge or -1
     */
    public function getWeight(int $start, int $dest)
    {
        if (!$this->hasOutgoingEdges($start)) return -1;
        $edge = $this->getEdge($start, $dest);
        if (is_bool($edge) && !$edge) return -1;
        return $this->weight[$edge];
    }

    /**
     * Returns the distance from a certain node to dest
     *
     * @param int $dest the destination node
     * @return mixed the distance form a certain node(specified in Dijkstra class)
     */
    public function getDistance(int $dest)
    {
        return $this->distance[$dest];
    }

    /**
     * sets the distance to dest and checks if value has changed
     *
     * @param int $dest the destination node
     * @param $dist mixed the distance
     * @return bool returns true if value has changed else false
     */
    public function setDistance(int $dest, $dist)
    {
        $val = $this->getDistance($dest);
        if ($val == $dist) return false;
        $this->distance[$dest] = $dist;
        return true;
    }

    /**
     * returns a list of all nodes
     *
     * @return array off all nodes
     */
    public function getNodeList()
    {
        $nodes = array();

        for ($i = 0; $i < count($this->offset); $i++) {
            if ($this->offset[$i] != null) $nodes[$i] = true;
        }
        for ($i = 0; $i < count($this->edges); $i++) {
            if ($this->edges[$i] != null) $nodes[$this->edges[$i]] = true;
        }
        return $nodes;
    }
    //TODO: removeEdge(Node 1, Node 2):void
}