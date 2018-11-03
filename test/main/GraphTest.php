<?php
/**
 * Created by PhpStorm.
 * User: otakupasi
 * Date: 11/2/18
 * Time: 5:00 PM
 */

namespace main;

require "../../src/main/Graph.php";

use Navigator\Graph;
use PHPUnit\Framework\TestCase;

class GraphTest extends TestCase
{

    public function testSetOffset()
    {
        try {
            $graph = new Graph();
            $graph->addEdge(0, 1, 1);
            $this->assertEquals(0, $graph->getOffset(0));
            $graph->setOffset(0, 5);
            $this->assertEquals(5, $graph->getOffset(0));
        } catch (\Exception $exception) {
            $this->fail($exception->getTraceAsString());
        }
    }

    public function testHasEdge()
    {
        try {
            $graph = new Graph();
            $this->assertFalse($graph->hasEdge());
            $graph->addEdge(0, 1, 1);
            $this->assertTrue($graph->hasEdge());
        } catch (\Exception $exception) {
            $this->fail($exception->getTraceAsString());
        }
    }

    public function testGetWeight()
    {
        try {
            $graph = new Graph();
            $this->assertEquals(-1, $graph->getWeight(0, 1));
            $graph->addEdge(0, 1, 1);
            $this->assertEquals(1, $graph->getWeight(0, 1));
            $graph->addEdge(0, 2, 10);
            $this->assertEquals(10, $graph->getWeight(0, 2));
        } catch (\Exception $exception) {
            $this->fail($exception->getTraceAsString());
        }
    }

    public function testCountOutgoingEdges()
    {
        try {
            $graph = new Graph();
            $this->assertEquals(0, $graph->countOutgoingEdges(0));
            $graph->addEdge(0, 1, 1);
            $this->assertEquals(1, $graph->countOutgoingEdges(0));
            $graph->addEdge(1, 2, 1);
            $graph->addEdge(0, 2, 1);
            $this->assertEquals(2, $graph->countOutgoingEdges(0));
            $this->assertEquals(1, $graph->countOutgoingEdges(1));
        } catch (\Exception $exception) {
            $this->fail($exception->getTraceAsString());
        }
    }

    public function testGetOffset()
    {
        try {
            $graph = new Graph();
            $this->assertEquals(-1, $graph->getOffset(0));
            $graph->addEdge(0, 1, 1);
            $this->assertEquals(0, $graph->getOffset(0));
            $graph->addEdge(1, 2, 1);
            $this->assertEquals(1, $graph->getOffset(1));
            $graph->addEdge(0, 2, 2);
            $this->assertEquals(0, $graph->getOffset(0));
            $this->assertEquals(2, $graph->getOffset(1));
        } catch (\Exception $exception) {
            $this->fail($exception->getTraceAsString());
        }
    }

    public function testGetEdge()
    {
        try {
            $graph = new Graph();
            $this->assertFalse($graph->getEdge(0, 1));
            $graph->addEdge(0, 1, 1);
            $this->assertEquals(0, $graph->getEdge(0, 1));
            $graph->addEdge(1, 2, 1);
            $this->assertEquals(1, $graph->getEdge(1, 2));
            $graph->addEdge(0, 2, 2);
            $this->assertEquals(1, $graph->getEdge(0, 2));
            $this->assertEquals(2, $graph->getEdge(1, 2));
        } catch (\Exception $exception) {
            $this->fail($exception->getTraceAsString());
        }
    }

    public function testHasOutgoingEdges()
    {
        try {
            $graph = new Graph();
            $this->assertFalse($graph->hasOutgoingEdges(0));
            $graph->addEdge(0, 1, 1);
            $this->assertTrue($graph->hasOutgoingEdges(0));
            $this->assertFalse($graph->hasOutgoingEdges(1));
        } catch (\Exception $exception) {
            $this->fail($exception->getMessage());
        }
    }

    public function testNextNode()
    {
        $graph = new Graph();
        $this->assertEquals(-1, $graph->nextNode(0));
        $graph->addEdge(0, 1, 5);
        $this->assertEquals(1, $graph->nextNode(0));
        $graph->addEdge(0, 2, 10);
        $this->assertEquals(1, $graph->nextNode(0));

    }

    public function testAddEdge()
    {
        try {
            $graph = new Graph();
            $this->assertFalse($graph->hasEdge());
            $graph->addEdge(0, 1, 1);
            $this->assertTrue($graph->hasEdge());
            $this->assertTrue($graph->hasOutgoingEdges(0));
            $this->assertEquals(0, $graph->getEdge(0, 1));
            $graph->addEdge(1, 2, 1);
            $this->assertTrue($graph->hasOutgoingEdges(1));
            $this->assertEquals(1, $graph->getEdge(1, 2));
        } catch (\Exception $exception) {
            $this->fail($exception->getMessage());
        }
    }
}
