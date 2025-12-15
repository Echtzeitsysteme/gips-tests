\ LP format - for model browsing. Use MPS format to capture full model detail.
\ Signature: 0x5c535b7a1d10cceb
Maximize
  n2n#0 + n2n#9 + n2n#7 + n2n#8 + n2n#5 + n2n#6 + n2n#3 + n2n#4 + n2n#1
   + n2n#2
Subject To
 MappingConstraint1Onn2n_0: n2n#0->usedVar = 42
 MappingConstraint1Onn2n_1: n2n#8->usedVar = 42
 MappingConstraint1Onn2n_2: n2n#5->usedVar = 42
 MappingConstraint1Onn2n_3: n2n#9->usedVar = 42
 MappingConstraint1Onn2n_4: n2n#7->usedVar = 42
 MappingConstraint1Onn2n_5: n2n#1->usedVar = 42
 MappingConstraint1Onn2n_6: n2n#2->usedVar = 42
 MappingConstraint1Onn2n_7: n2n#6->usedVar = 42
 MappingConstraint1Onn2n_8: n2n#3->usedVar = 42
 MappingConstraint1Onn2n_9: n2n#4->usedVar = 42
 TypeConstraint0OnVirtualResourceNode_0: n2n#3 = 1
 TypeConstraint0OnVirtualResourceNode_1: n2n#0 = 1
 TypeConstraint0OnVirtualResourceNode_2: n2n#1 = 1
 TypeConstraint0OnVirtualResourceNode_3: n2n#5 = 1
 TypeConstraint0OnVirtualResourceNode_4: n2n#7 = 1
 TypeConstraint0OnVirtualResourceNode_5: n2n#9 = 1
 TypeConstraint0OnVirtualResourceNode_6: n2n#8 = 1
 TypeConstraint0OnVirtualResourceNode_7: n2n#2 = 1
 TypeConstraint0OnVirtualResourceNode_8: n2n#6 = 1
 TypeConstraint0OnVirtualResourceNode_9: n2n#4 = 1
Bounds
 -1e+06 <= n2n#0->usedVar <= 1e+06
 -1e+06 <= n2n#9->usedVar <= 1e+06
 -1e+06 <= n2n#7->usedVar <= 1e+06
 -1e+06 <= n2n#8->usedVar <= 1e+06
 -1e+06 <= n2n#5->usedVar <= 1e+06
 -1e+06 <= n2n#6->usedVar <= 1e+06
 -1e+06 <= n2n#3->usedVar <= 1e+06
 -1e+06 <= n2n#4->usedVar <= 1e+06
 -1e+06 <= n2n#1->usedVar <= 1e+06
 -1e+06 <= n2n#2->usedVar <= 1e+06
Binaries
 n2n#0 n2n#9 n2n#7 n2n#8 n2n#5 n2n#6 n2n#3 n2n#4 n2n#1 n2n#2
End
