package com.github.scova0731.petstore4s.step1.mapper

import com.github.scova0731.petstore4s.step1.domain.Sequence


trait SequenceMapper {

  def getSequence(sequence: Sequence): Sequence

  def updateSequence(sequence: Sequence): Unit
}
