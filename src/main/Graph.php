<?php
/**
 * Created by PhpStorm.
 * User: otakupasi
 * Date: 11/1/18
 * Time: 2:25 PM
 */

namespace Navigator;
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
     * @param int $start the start node
     * @param int $dest the destination node
     * @param int $weight the weight for the node
     */

    public function addEdge(int $start, int $dest, int $weight)
    {
        /*
        //get the next start nodes offset
        $index = $this->getOffset($start + 1);
        print $start.":".count($this->edges);
        if ($this->hasEdge()) {

            //increase offset by 1
            for ($i = $index; $i < count($this->offset); $i++) {
                $val = $this->offset[$i];
                $this->setOffset($i, $val + 1);
            }
            //move edges to right by 1
            for ($i = count($this->edges); $i >= $index; $i--) {
                $this->edges[$i + 1] = $this->edges[$i];
            }
        } else {
            $this->setOffset($start, 0);
            $index=0;
        }
        //insert data into array
        $this->edges[$index] = $dest;
        $this->weight[$index] = $weight;
        */
    }

    public function countOutgoingEdges(int $node)
    {
        $n = 0;
        if ($this->getOffset($node + 1) == -1)
            return count($this->edges) - $this->getOffset($node);
        for ($i = $this->getOffset($node + 1); $i < $this->getOffset($node + 1); $i++) {
            $n++;
        }
        return $n;
    }

    public function hasEdge()
    {
        return isset($this->edges[0]) && $this->edges[0] != null;
    }

    /**
     * @param int $node the node
     * @return int returns the offset or -1
     */
    public function getOffset(int $node)
    {
        if (!isset($this->offset[$node]) || $this->offset[$node] == null) return -1;
        return $this->offset[$node];
    }

    /**
     * @param int $node the node
     * @param int $offset the offset for the node
     */
    public function setOffset(int $node, int $offset)
    {
        $this->offset[$node] = $offset;
    }

    /**
     * @param int $edge the index from the edges array
     * @return int returns the weight of the edge or -1
     */
    public function getWeight(int $edge)
    {
        if ($this->weight[$edge] == null || empty($this->weight)) return -1;
        return $this->weight[$edge];
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
        if ($to == -1) $to = count($this->offset);
        //for all those edges search if destination is same
        for ($i = $from; $i < $to; $i++) {
            //return edge index if found
            if ($this->edges[$i] == $dest) return $i;
        }
        //if nothing found return
        return false;
    }

    //TODO: hasPath(Node 1, Node 2):bool
    //TODO: nextNode(Node node, criteria):Node  criteria as constant like NEXT_GEO or NEXT_WEIGHT
    //TODO: sort(criteria)
    //TODO: removeEdge(Node 1, Node 2):void
}