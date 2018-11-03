<?php
/**
 * Created by PhpStorm.
 * User: otakupasi
 * Date: 11/1/18
 * Time: 2:26 PM
 */

use Navigator\Graph;

class Dijkstra
{
    public $startTime;
    private $graph;
    private $startNode;

    public function __construct(Graph &$graphInstance, int $start)
    {
        $this->graph = $graphInstance;
        $this->startNode = $start;
    }

    public function init()
    {
        $nodeList = $this->graph->getNodeList();
        foreach ($nodeList as $node) {
            $this->graph->setDistance($node, INF);
        }
        $this->graph->setDistance($this->startNode, 0);
    }

    public function start()
    {
        $this->startTime = new DateTime();
    }
    //TODO: run(Node start):void
    //calls Graph->setDistance(Node start, Node dest) thus no need to return anything
    //TODO: assertTimeout: run under 20 seconds

}