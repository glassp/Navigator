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
     * Checks if the graph has edges
     *
     * @return bool returns true if graph has edges
     */
    public function hasEdge()
    {
        return !empty($this->edges);
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

    //TODO: nextNode(Node node, criteria):Node  criteria as constant like NEXT_GEO or NEXT_WEIGHT
    //TODO: sort(criteria)
    //TODO: removeEdge(Node 1, Node 2):void
}