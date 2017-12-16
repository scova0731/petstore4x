package com.github.scova0731.petstore4s.step2.repository

import com.github.scova0731.petstore4s.step2.domain.Sequence


trait SequenceRepository {

  def getSequence(sequence: Sequence): Sequence

  def updateSequence(sequence: Sequence): Unit
}
