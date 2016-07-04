# Huffman Compresion

## How to use it:

`java -jar huffman.jar (parameters)`

Parameters:

`(compress | uncompress) sourceFile destinationFile`

Example:

`java -jar huffman.jar compress toCompress compressed`

`java -jar huffman.jar uncompress compressed uncompressed`

## About the protocol:

compressFile = sizeOfDataInBits | treeInformation | compressFile

sizeOfDataInBits: 8 bytes (signed long)

treeInformation = nodeRepresentation | nodeRepresentation | ... | nodeRepresentation

*	The nodes representation starts with the root and transverse the tree using dfs, going first to the left
		child and then to the right child.

nodeRepresentation: (0|1) [nodeData]
	
*	0 if the node is an inner node.
*	1 if the node is a leaf, and in that case, follows a byte with the data of the node.
	
compressFile: compressBytes

*	*compressBytes* represent the original bytes mapped to the compress representation of them.

## Notes:

*	The extra information added with the header (*sizeOfDataInBits* + *treeInformation*) will never be
 greater than 328 bytes and the *compressFile* will never be greater than the original one. In other words, the uncompress file in the worst case scenario will be only 328 bytes bigger.


