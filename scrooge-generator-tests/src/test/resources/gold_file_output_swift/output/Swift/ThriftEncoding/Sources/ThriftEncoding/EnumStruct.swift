//
// Automatically @generated by Scrooge
//
// DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
//
import Foundation
import TwitterApacheThrift
public struct EnumStruct: Hashable {
    public var enumValue: SomeEnum
    enum CodingKeys: Int, CodingKey {
        case enumValue = 1
    }
    public init(enumValue: SomeEnum) {
        self.enumValue = enumValue
    }
}
extension EnumStruct: ThriftCodable {}