//
// Automatically @generated by Scrooge
//
// DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
//
import Foundation
import TwitterApacheThrift
struct FoundationThriftStruct: Hashable {
    var boolValue: Bool
    var doubleValue: Double
    var int16Value: Int16
    var int32Value: Int32
    var int64Value: Int64
    var stringValue: String
    enum CodingKeys: Int, CodingKey {
        case boolValue = 1
        case doubleValue = 2
        case int16Value = 3
        case int32Value = 4
        case int64Value = 5
        case stringValue = 6
    }
    init(boolValue: Bool, doubleValue: Double, int16Value: Int16, int32Value: Int32, int64Value: Int64, stringValue: String) {
        self.boolValue = boolValue
        self.doubleValue = doubleValue
        self.int16Value = int16Value
        self.int32Value = int32Value
        self.int64Value = int64Value
        self.stringValue = stringValue
    }
}
extension FoundationThriftStruct: ThriftCodable {}