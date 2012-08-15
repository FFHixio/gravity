/*
 * GravityDataProduct.h
 *
 *  Created on: Aug 14, 2012
 *      Author: Chris Brundick
 */

#ifndef GRAVITYDATAPRODUCT_H_
#define GRAVITYDATAPRODUCT_H_

#include "GravityDataProductPB.pb.h"

namespace gravity
{

using namespace std;

/**
 * Generic Data Product for the Gravity Infrastructure
 */
class GravityDataProduct
{
private:
	GravityDataProductPB* gravityDataProductPB; ///< internal protobuf representation of data product
	string filterText; ///< text string on which subscribers of this data product can apply a filter
public:
	/**
	 * Constructor
	 * \param dataProductID string descriptor for this data product. Name by which subscribers will configure subscriptions
	 */
	GravityDataProduct(string dataProductID);

	/**
	 * Default Destructor
	 */
	virtual ~GravityDataProduct();

	/**
	 * Method to return the timestamp associated with this data product
	 * \return timestamp for data
	 */
	uint64_t getTimestamp();

	/**
	 * Method to return the data product ID for this data
	 * \return data product ID
	 */
	string getDataProductID();

	/**
	 * Setter for the specification of software version information
	 * \param softwareVersion string specifying the version number
	 */
	void setSoftwareVersion(string softwareVersion);

	/**
	 * Getter for the software version specified on this data product
	 * \return the software version number associated with this data product
	 */
	string getSoftwareVersion();

	/**
	 * Setter for the filter-able text associated with this data product
	 * \param filterText text string that can be filtered against by subscribers
	 */
	void setFilterText(string filterText);

	/**
	 * Getter for the filter-able text associated with this data product
	 * \return filterText text string that can be filtered against by subscribers
	 */
	string getFilterText();

	/**
	 * Set the application-specific data for this data product
	 * \param data pointer to arbitrary data
	 * \param len length of data
	 */
	void setData(void* data, int len);

	/**
	 * Set the application-specific data for this data product
	 * \param data A Google Protocol Buffer Message object containing the data
	 */
	void setData(google::protobuf::Message& data);

	/**
	 * Getter for the application-specific data contained within this data product
	 * \return pointer to the data
	 */
	void* getData();

	/**
	 * Get the size of the data contained within this data product
	 * \return size in bytes of contained data
	 */
	int getDataSize();

	/**
	 * Populate a protobuf object with the data contained in this data product
	 * \param data Google Protocol Buffer Message object to populate
	 */
	void populateMessage(google::protobuf::Message& data);
};

} /* namespace gravity */
#endif /* GRAVITYDATAPRODUCT_H_ */
